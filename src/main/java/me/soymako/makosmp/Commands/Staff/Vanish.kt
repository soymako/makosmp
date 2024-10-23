package me.soymako.makosmp.Commands.Staff

import me.soymako.makosmp.Custom.MsmpPlayer
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Vanish : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player){
            var player = MsmpPlayer(sender)
            if (player.player.isOp){
                player.vanish = !player.vanish
            }
        }


        return false
    }
}