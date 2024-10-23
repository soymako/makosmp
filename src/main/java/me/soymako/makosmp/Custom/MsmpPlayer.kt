package me.soymako.makosmp.Custom

import me.soymako.makomc.Chat
import me.soymako.makosmp.Events.ChatColorsObtained
import me.soymako.makosmp.Main
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.entity.Mob
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
import java.text.NumberFormat
import java.util.*

class MsmpPlayer(val player: Player) {

    private var formatter: NumberFormat = NumberFormat.getNumberInstance(Locale.US).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }

    private var dc:PersistentDataContainer = player.persistentDataContainer

    var name:String = player.name

    var fullName:String = chatNamePrefix + name

    class Namespaces{
        var registered:NamespacedKey = NamespacedKey(Main.instance!!, "registered")
        var logged:NamespacedKey = NamespacedKey(Main.instance!!, "logged")
        var canMove:NamespacedKey = NamespacedKey(Main.instance!!, "can_move")
        var chatColors:NamespacedKey = NamespacedKey(Main.instance!!, "chat_colors")
        var chatNamePrefix:NamespacedKey = NamespacedKey(Main.instance!!, "chat_name_prefix")
        var canChangeChatPrefix:NamespacedKey = NamespacedKey(Main.instance!!, "can_change_chat_prefix")

        var maxHomes = NamespacedKey(Main.instance!!, "max_homes")

        var kills = NamespacedKey(Main.instance!!, "kills")
        var deaths = NamespacedKey(Main.instance!!, "deaths")

        var vanish = NamespacedKey(Main.instance!!, "vanish")
        var fly = NamespacedKey(Main.instance!!, "fly")
    }

    var canMove:Boolean
        get() {
            return dc.get(Namespaces().canMove, PersistentDataType.BOOLEAN) ?: false
        }
        set(value) {
            dc.set(Namespaces().canMove, PersistentDataType.BOOLEAN, value)
        }

    var logged: Boolean
        get(){
            return dc.get(Namespaces().logged, PersistentDataType.BOOLEAN) ?: false
        }
        set(v) {
            dc.set(Namespaces().logged, PersistentDataType.BOOLEAN, v)
        }

    var registered:Boolean
        get() {
            return dc.get(Namespaces().registered, PersistentDataType.BOOLEAN) ?: false
        }
        set(value) {
            dc.set(Namespaces().registered, PersistentDataType.BOOLEAN, value)
        }

    var chatColors: Boolean
        get() {
            return dc.get(Namespaces().chatColors, PersistentDataType.BOOLEAN) ?: false
        }
        set(value) {
            dc.set(Namespaces().registered, PersistentDataType.BOOLEAN, value)
            val chatColor = ChatColorsObtained(this, value)
            Bukkit.getPluginManager().callEvent(chatColor)
        }

    var canChangePrefix:Boolean
        get() {
            return dc.get(Namespaces().canChangeChatPrefix, PersistentDataType.BOOLEAN)  ?: false
        }
        set(value) {
            dc.set(Namespaces().canChangeChatPrefix, PersistentDataType.BOOLEAN, value)
            if (value){
                Chat().succes(player, "Ahora puedes cambiar el prefijo de tu nombre en el chat! Usa &e/prefijo &a<texto> &fpara cambiar tu prefijo!", true)
            }
            else{
                Chat().alerta(player, "Tu permiso para cambiar prefijos se ha revocado. Si crees que esto es un error, contactate con un staff", true)
            }
        }

    var maxHomes:Int
        get() {
            return dc.get(Namespaces().maxHomes, PersistentDataType.INTEGER) ?: 1
        }
        set(value) {
            val previousHomes:Int = maxHomes
            dc.set(Namespaces().maxHomes, PersistentDataType.INTEGER, value)
            if (previousHomes < value){
                Chat().playerMessage(player, "&aTu limite de casas ha sido aumentado a $value")
            }
            else{
                Chat().playerMessage(player, "&cTu limite de casas ha sido reducido a $value")
            }
        }

    var chatNamePrefix:String
        get(){
            return dc.get(Namespaces().chatNamePrefix, PersistentDataType.STRING) ?: ""
        }
        set(value) {
            dc.set(Namespaces().chatNamePrefix, PersistentDataType.STRING, value)
            Chat().succes(player, "Se ha cambiado tu prefijo del chat correctamente a: $value", true)
            player.setPlayerListName(value + name)
        }

    var dinero:Double
        get() = Main.playerData.getDinero(this)
        set(value) = Main.playerData.setDinero(this, value)


    var kills:Int
        get() = dc.get(Namespaces().kills, PersistentDataType.INTEGER) ?: 0
        set(value) {
            dc.set(Namespaces().kills, PersistentDataType.INTEGER, value)
        }

    var deaths:Int
        get() = dc.get(Namespaces().deaths, PersistentDataType.INTEGER) ?: 0
        set(value) {
            dc.set(Namespaces().deaths, PersistentDataType.INTEGER, value)
        }


    var vanish: Boolean
        get() = dc.get(Namespaces().vanish, PersistentDataType.BOOLEAN) ?: false
        set(value) {
            dc.set(Namespaces().vanish, PersistentDataType.BOOLEAN, value)
            setVanishLogic(value)
//            Chat().succes(player, "Vanish: $value", true)
            fly = value
            if (value){
                for (w in Bukkit.getWorlds()){
                    for (e in w.livingEntities){
                        if (e is Mob){
                            e.target = null
                        }
                    }
                }
            }
        }


    fun setVanishLogic(show:Boolean){
        player.isGlowing = show
        if (show){
            for (p in Bukkit.getOnlinePlayers()){
                p.hidePlayer(Main.instance!!, player)
            }
        }
        else{
            var c = TextComponent("")
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, c)
            for (p in Bukkit.getOnlinePlayers()){
                p.showPlayer(Main.instance!!, player)
            }
        }
    }


    var fly: Boolean
        get() = dc.get(Namespaces().fly, PersistentDataType.BOOLEAN) ?: false
        set(value) {
            dc.set(Namespaces().fly, PersistentDataType.BOOLEAN, value)
            player.allowFlight = value
//            Chat().succes(player, "Volar: $value", true)
        }


















    fun auth(){
        if (!registered or !logged){
            canMove = false
        }
        if (!registered){
            Chat().playerMessage(player, "Porfavor usa &e/register <contraseña> <contraseña>&f para registrarte")
        }
        if (!logged){
            Chat().playerMessage(player, "Porfavor usa &e/login <contraseña>&f para entrar")
        }
    }

    fun displayWelcomeTitle(){
        player.sendTitle(Main.serverData.welcomeMessage, null, 0, 100, 50)
    }

    fun removeDinero(v:Double){
        var realV = v
        if (v > dinero) realV = dinero
        Main.playerData.removeDinero(this, realV)
        sendMessage("&c-$${formatter.format(realV)}")
    }
    fun addDinero(v:Double){
        Main.playerData.addDinero(this, v)
        sendMessage("&a+$${formatter.format(v)}")
    }

    fun robarDinero(victima:MsmpPlayer, v:Double){
        if (v >= victima.dinero){
            this.addDinero(victima.dinero)
            victima.removeDinero(victima.dinero)
            sendMessage("&2+$${formatter.format(v.dec())} ✋")
            victima.sendMessage("&4-$${formatter.format(v.dec())} ✋")
        }
        else{
            this.addDinero(v)
            victima.removeDinero(v)
            sendMessage("&2+$${formatter.format(v.dec())} ✋")
            victima.sendMessage("&4-$${formatter.format(v.dec())} ✋")
        }
        victima.player.world.playSound(victima.player.location, Sound.ENTITY_PLAYER_ATTACK_NODAMAGE, SoundCategory.PLAYERS, 1f,1f)
        victima.player.world.playSound(victima.player.location, Sound.ENTITY_PLAYER_ATTACK_WEAK, SoundCategory.PLAYERS, .5f,.5f)
    }

    fun sendMessage(message:String){
        Chat().playerMessage(player, message)
    }

    fun getKDR(): Float{
        return runCatching {
            if (kills == 0) return 0.0f
            (deaths.toFloat() / kills.toFloat())
        }.getOrElse {
            0.0f
        }
    }


}