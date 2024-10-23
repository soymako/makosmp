package me.soymako.makosmp.Commands

import me.soymako.makomc.Chat
import me.soymako.makomc.Sounds
import me.soymako.makosmp.Custom.Home
import me.soymako.makosmp.Custom.MsmpPlayer
import me.soymako.makosmp.Main
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class HomeCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player){
            var player:MsmpPlayer = MsmpPlayer(sender as Player)
            if (args.isNotEmpty()){
                var homeName:String = args[0]
                var home:Home? = Main.homeData.getHome(player.player.name, homeName)
                if (home != null){
                    Sounds().succes(player.player)
                    player.player.teleport(home.location)
                }
                else{
                    Chat().commandError(player.player, "No tienes ninguna casa con el nombre de: $homeName", "/home <nombre>", true)
                }
            }
            else{
                var homeName:String? = Main.homeData.getHomeListString(player.player.name)?.random()
                if (homeName == null){
                    Chat().commandError(player.player, "No se pudo encontrar casa...", "", true)
                    return false
                }
                var home:Home? = Main.homeData.getHome(player.player.name, homeName!!)
                player.player.teleport(home!!.location)
                Chat().succes(player.player, "&aTeletransportado a &e${home.name}", true)

                return true
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
        if (sender is Player){
            var player:MsmpPlayer = MsmpPlayer(sender as Player)
            if (args.size == 1){
                return Main.homeData.getHomeListString(player.player.name)
            }
        }


        return null
    }
}
