package me.soymako.makosmp.Commands.Staff

import me.soymako.makomc.Chat
import me.soymako.makosmp.Custom.MsmpPlayer
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class teleport : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player){
            val player = MsmpPlayer(sender)
            if (player.player.isOp || player.vanish){
                if (args.isNotEmpty()){
                    var target = kotlin.runCatching {
                        MsmpPlayer(Bukkit.getPlayer(args[0])!!)
                    }.getOrElse {
                        Chat().commandError(player.player, "Jugador no esta en linea", "", true)
                        return false
                    }

                    player.player.teleport(target.player.location)
                    Chat().succes(player.player, "Te has teletransportado a ${target.name}", true)
                }
            }
            else{
                Chat().commandNotAllowed(player.player, null, true)
            }
        }


        return false
    }
}