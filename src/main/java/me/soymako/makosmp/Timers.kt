package me.soymako.makosmp

import me.soymako.makomc.Chat
import me.soymako.makosmp.Custom.MsmpPlayer
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

class Timers {

    fun tickTimer(){
        object : BukkitRunnable(){
            override fun run() {
                for (p in Bukkit.getOnlinePlayers()){
                    var player = MsmpPlayer(p)
                    if (player.vanish){
                        var c = TextComponent(Chat().translate("&e&lVanish: &r&a${player.vanish}"))
                        player.player.spigot().sendMessage(ChatMessageType.ACTION_BAR, c)
                    }
                }
            }

        }.runTaskTimer(Main.instance!!, 1,1)
    }

}