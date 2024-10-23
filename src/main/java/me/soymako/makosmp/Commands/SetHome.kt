package me.soymako.makosmp.Commands

import me.soymako.makomc.Chat
import me.soymako.makosmp.Custom.Home
import me.soymako.makosmp.Custom.MsmpPlayer
import me.soymako.makosmp.Main
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetHome : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player){
            var player:MsmpPlayer = MsmpPlayer(sender as Player)
            // /sethome <name>

            if (Main.homeData.getHomesAmount(player.player.name) >= player.maxHomes){
                Chat().alerta(player.player, "Llegaste al limite de casas que puedes tener...", true)
                return false;
            }

            if (args.isNotEmpty()){

                var homeName:String = args[0]

                var home:Home = Home(homeName, player.player.location)
                Main.homeData.saveHome(player.player.name, home)
                Chat().succes(player.player, "Casa guardada correctamente como: &e$homeName", true)
            }
        }

        return false
    }
}