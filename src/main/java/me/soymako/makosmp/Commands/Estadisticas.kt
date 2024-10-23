package me.soymako.makosmp.Commands

import me.soymako.makomc.Chat
import me.soymako.makosmp.Custom.MsmpPlayer
import me.soymako.makosmp.Inventories
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Estadisticas : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player){
            var player:MsmpPlayer = MsmpPlayer(sender)

            if (args.isNotEmpty()){

                var target:Player = kotlin.runCatching {
                    Bukkit.getPlayer(args[0])!!
                }.getOrElse {
                    Chat().commandError(player.player, "El jugador no está conectado, debido a la forma en la que almacenamos los datos, solo puedes ver estadísticas de jugadores conectados.", "", true)
                    return false
                }
                Inventories().statsInventory(player, MsmpPlayer(target!!))
                return true
            }

            Inventories().statsInventory(player, player)
        }



        return false
    }
}