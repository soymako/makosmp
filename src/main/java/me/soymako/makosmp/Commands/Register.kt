package me.soymako.makosmp.Commands

import me.soymako.makomc.Chat
import me.soymako.makosmp.Custom.MsmpPlayer
import me.soymako.makosmp.Events.PlayerRegistered
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Register : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player){
            var player:MsmpPlayer = MsmpPlayer(sender as Player)
            if (player.registered){
                Chat().commandError(player.player, "No hay necesidad de registrarse de nuevo", "", true)
                return false
            }
            if (args.size > 1){
                var p1:String = args[0]
                var p2:String = args[1]
                if (p1 == p2){
                    var registerEvent:PlayerRegistered = PlayerRegistered(player, p1)
                    Bukkit.getPluginManager().callEvent(registerEvent)
                }
                else{
                    Chat().commandError(player.player, "Las contraseñas no coinciden!", "/register <contraseña> <contraseña>", true)
                }
            }
            else{
                Chat().commandError(player.player, "No colocaste ninguna contraseña", "/register <contraseña> <contraseña>", true)
            }
        }
        return false
    }
}