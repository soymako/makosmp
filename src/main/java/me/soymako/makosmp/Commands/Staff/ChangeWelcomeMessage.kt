package me.soymako.makosmp.Commands.Staff

import me.soymako.makomc.Chat
import me.soymako.makosmp.Main
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
class ChangeWelcomeMessage : CommandExecutor{
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            var player: Player = sender as Player
            if (player.isOp) {
                if (args.isNotEmpty()) {
                    var newMessage: String = Chat().translate(args[0])
                    Main.serverData.welcomeMessage = newMessage
                    Chat().succes(player, "Valor cambiado con exito!", true)
                    return true
                }
            }
        }


        TODO("Not yet implemented")
        return false
    }

}

