package me.soymako.makosmp.Listeners

import me.soymako.makomc.Chat
import me.soymako.makosmp.Custom.MsmpPlayer
import me.soymako.makosmp.Events.ChatColorsObtained
import me.soymako.makosmp.Events.PlayerLogged
import me.soymako.makosmp.Events.PlayerRegistered
import me.soymako.makosmp.Main
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerListener : Listener {

    @EventHandler
    fun onPlayerJoined(e:PlayerJoinEvent){
        var player:MsmpPlayer = MsmpPlayer(e.player)
        player.auth()
    }

    @EventHandler
    fun onPlayerDisconnected(e:PlayerQuitEvent){
        var player:MsmpPlayer = MsmpPlayer(e.player)
        player.logged = false
        player.canMove = false
    }

    @EventHandler
    fun onPlayerMovement(e:PlayerMoveEvent){
        var player:MsmpPlayer = MsmpPlayer(e.player)
//        player.player.sendMessage("puedo moverme: ${player.canMove}")
        if (!player.canMove){
            e.isCancelled = true
            player.auth()
        }
    }

    @EventHandler
    fun onPlayerRegistered(e:PlayerRegistered){
        var player:MsmpPlayer = e.player
        Main.playerData.setPassword(player.player.name, e.password)
        player.registered = true
        var loggedEvent:PlayerLogged = PlayerLogged(player, e.password)
        Bukkit.getPluginManager().callEvent(loggedEvent)
    }

    @EventHandler
    fun onPlayerLogged(e:PlayerLogged){
        var player:MsmpPlayer = e.player
        player.canMove = true
        player.displayWelcomeTitle()
    }

    @EventHandler
    fun onPlayerMessageAsync(e:AsyncPlayerChatEvent){
        var player:MsmpPlayer = MsmpPlayer(e.player)
        var message:String = e.message

        if (player.chatColors){
            message = Chat().translate(message)
        }

        for (p:Player in Bukkit.getOnlinePlayers()){
            if (!Main.playerData.hasPlayerBlocked(p.name, player.player.name)){
                p.sendMessage("${player.chatNamePrefix} ${player.player.name} ⏵ $message")
            }
        }

        e.isCancelled = true
    }

    @EventHandler
    fun onChatColorsObtained(e:ChatColorsObtained){
        var player = e.player
        if (e.value){
            Chat().succes(player.player, "Ahora puedes escribir con colores usando '&'", true)
        }
        else{
            Chat().alerta(player.player, "Se te ha quitado la habilidad de usar colores en el chat. Si crees que es un error ponte en contacto con un staff", true)
        }
    }

}