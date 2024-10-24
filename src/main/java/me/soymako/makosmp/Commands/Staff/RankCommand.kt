package me.soymako.makosmp.Commands.Staff

import me.soymako.makosmp.Custom.MsmpPlayer
import me.soymako.makosmp.Types.Rank
import me.soymako.makosmp.Types.Ranks
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class RankCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player){
            val player = MsmpPlayer(sender)
            if (player.permissionLevel >= Ranks.ADMIN.permissionLevel || player.player.isOp){
                // /rank <set/remove> <rank>
                if (args.size == 2){

                    val option:String = args[0]
                    val rank: Ranks = Ranks.valueOf(args[1])

                    when{
                        option == "set" -> player.rank = rank
                        option == "remove" -> player.rank = Ranks.USUARIO
                        option == "get" -> player.sendMessage("rango: ${player.rank.toString()} con un permiso de: ${player.rank.permissionLevel}")
                    }

                }
                // /rank <player> <set/remove> <rank>
                if (args.size == 3){

                }

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

        if (args.size == 1){
            return mutableListOf<String>("set", "remove", "get")
        }
        if (args.size == 2){
            return Ranks.values().map { it.name }.toMutableList()
        }

        return null
    }
}