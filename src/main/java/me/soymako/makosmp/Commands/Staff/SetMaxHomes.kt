package me.soymako.makosmp.Commands.Staff

import me.soymako.makomc.Chat
import me.soymako.makosmp.Custom.MsmpPlayer
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetMaxHomes : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player){
            var player:MsmpPlayer = MsmpPlayer(sender as Player)
            if (player.player.isOp){
                if (args.size > 1){
                    var target:MsmpPlayer? = runCatching {
                        MsmpPlayer(Bukkit.getPlayer(args[0])!!)
                    }.getOrElse {
                        Chat().commandNotAllowed(player.player, "No se pudo encontrar al jugador...", true)
                        return false
                    }
                    var maxHomes:Int = runCatching {
                        args[1].toInt()
                    }.getOrElse {
                        Chat().commandError(player.player, "El valor tiene que ser un ENTERO", "/set-max-homes <player> <homes>", true)
                        return false
                    }

                    target?.maxHomes = maxHomes
                }
            }
        }


        return false
    }
}