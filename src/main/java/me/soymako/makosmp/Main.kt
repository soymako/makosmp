package me.soymako.makosmp

import MsmpInventory
import me.soymako.makosmp.Commands.*
import me.soymako.makosmp.Commands.Payed.Prefijo
import me.soymako.makosmp.Commands.Staff.*
import me.soymako.makosmp.Custom.Home
import me.soymako.makosmp.Data.HomeData
import me.soymako.makosmp.Data.PlayerData
import me.soymako.makosmp.Data.ServerData
import me.soymako.makosmp.Hook.ProtocolLibHook
import me.soymako.makosmp.Listeners.EntityListener
import me.soymako.makosmp.Listeners.InventoryListener
import me.soymako.makosmp.Listeners.PlayerListener
import me.soymako.makosmp.Types.Rank
import org.bukkit.Bukkit
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

//    10 -> helper
//    11 -> mod
//    12 -> admin
//    13 -> builder / developer
//    14 -> owner
//    15 -> OP




    companion object{
        var instance:Main? = null

        var serverData:ServerData = ServerData()
        var playerData:PlayerData = PlayerData()
        var homeData: HomeData = HomeData()
        var inventarios = mutableMapOf<Player, MsmpInventory>()
    }

    fun registerCustomDataTypes(){
        ConfigurationSerialization.registerClass(Home::class.java, "Home")
        ConfigurationSerialization.registerClass(Rank::class.java, "RankCommand")
    }

    fun initData(){
        serverData.init()
        playerData.init()
        homeData.init()
    }

    fun registerCommands(){
        getCommand("change_welcome_message")?.setExecutor(ChangeWelcomeMessage())
        getCommand("register")?.setExecutor(Register())
        getCommand("login")?.setExecutor(Login())
        getCommand("sethome")?.setExecutor(SetHome())
        getCommand("home")?.setExecutor(HomeCommand())
        getCommand("bloquear")?.setExecutor(Bloquear())
        getCommand("desbloquear")?.setExecutor(Desbloquear())
        getCommand("prefijo")?.setExecutor(Prefijo())
        getCommand("set-max-homes")?.setExecutor(SetMaxHomes())
        getCommand("dinero")?.setExecutor(Dinero())
        getCommand("estadisticas")?.setExecutor(Estadisticas())
        getCommand("vanish")?.setExecutor(Vanish())
        getCommand("teleport")?.setExecutor(teleport())
        getCommand("rank")?.setExecutor(RankCommand())
    }

    fun registerListeners(){

        server.pluginManager.registerEvents(PlayerListener(), this)
        server.pluginManager.registerEvents(InventoryListener(), this)
        server.pluginManager.registerEvents(EntityListener(), this)

    }

    override fun onEnable() {
        instance = this

        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null){
            ProtocolLibHook.register();
            println("[MSMP] --> registrando")
        }
        else{
            println("protocol lib es nulo")
        }

        if (Bukkit.getPluginManager().getPlugin("EssentialsX") != null){
            println("[MSMP] --> Essentials presente!")
        }

        registerCustomDataTypes()
        initData()
        registerCommands()
        registerListeners()

        Timers().tickTimer()
    }

    override fun onDisable() {
        instance = null
    }
}
