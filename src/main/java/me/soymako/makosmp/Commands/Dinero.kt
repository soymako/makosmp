package me.soymako.makosmp.Commands

import me.soymako.makomc.Chat
import me.soymako.makosmp.Custom.MsmpPlayer
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.text.NumberFormat
import java.util.*

class Dinero : CommandExecutor {


    var formato = NumberFormat.getNumberInstance(Locale.US).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player){
            var player:MsmpPlayer = MsmpPlayer(sender as Player)

            var formattedMoney = formato.format(player.dinero)

            Chat().playerMessage(player.player, "&adinero: &e$$formattedMoney")
            return true
        }


        return false
    }
}