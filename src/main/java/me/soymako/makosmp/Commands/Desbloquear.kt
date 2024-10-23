package me.soymako.makosmp.Commands

import me.soymako.makomc.Chat
import me.soymako.makomc.Utils
import me.soymako.makosmp.Custom.MsmpPlayer
import me.soymako.makosmp.Main
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class Desbloquear : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player){
            var player:MsmpPlayer = MsmpPlayer(sender as Player)
            if (args.isNotEmpty()){
                Main.playerData.unblockPlayers(player, args.toMutableList())
                return true
            }
            else{
                Chat().commandError(player.player, "Necesitas colocar a los jugadores que quieras bloquear", usage = "/bloquear <nombre> <nombre> <nombre>", true)
            }

        }


        return false
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>,
    ): MutableList<String>? {
        if (sender is Player) {
            val player = MsmpPlayer(sender as Player)
            if (args.isNotEmpty()) {
                return Utils().getUniqueElements(Main.playerData.getBlockedPlayers(player.player.name)!!.toMutableList(), args.toMutableList()).toMutableList()

            }
        }
        return null
    }

}