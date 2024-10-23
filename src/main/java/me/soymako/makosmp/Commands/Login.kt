package me.soymako.makosmp.Commands

import me.soymako.makomc.Chat
import me.soymako.makosmp.Custom.MsmpPlayer
import me.soymako.makosmp.Events.PlayerLogged
import me.soymako.makosmp.Main
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Login : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player){
            var player:MsmpPlayer = MsmpPlayer(sender as Player)
            if (player.logged){
                Chat().commandError(player.player, "Ya estás dentro", "", true)
                return false
            }
            if (args.isNotEmpty()){
                var password:String = args[0]
                if (password == Main.playerData.getPassword(player.player.name)){
                    var lE = PlayerLogged(player, password)
                    Bukkit.getPluginManager().callEvent(lE)
                }
            }
        }
        return false
    }
}