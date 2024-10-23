package me.soymako.makosmp.Commands.Payed

import me.soymako.makomc.Chat
import me.soymako.makosmp.Custom.MsmpPlayer
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Prefijo : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player){
            var player:MsmpPlayer = MsmpPlayer(sender as Player)
            if (player.player.isOp || player.canChangePrefix){
                if (args.isNotEmpty()){
                    var prefix:String = "&e&l[&r${args[0]}&e&l]&r"
                    player.chatNamePrefix = Chat().translate(prefix)
                }
            }
        }


        return false
    }
}