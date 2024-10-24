package me.soymako.makosmp.Hook

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.events.PacketEvent
import com.comphenix.protocol.wrappers.EnumWrappers
import com.comphenix.protocol.wrappers.PlayerInfoData
import com.comphenix.protocol.wrappers.WrappedChatComponent
import com.comphenix.protocol.wrappers.WrappedGameProfile
import me.soymako.makomc.Chat
import me.soymako.makosmp.Custom.MsmpPlayer
import me.soymako.makosmp.Main
import me.soymako.makosmp.Types.Ranks
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask
import java.util.*

public final class ProtocolLibHook {






    companion object {
        fun register() {
            println("registrando PLIBHOOK")
            val manager = ProtocolLibrary.getProtocolManager()



            // literalmente pude haber usado AsyncChatEvent de bukkit... Pero esto es "mas rapido", entonces a lo mejor
            // y es mas responsivo...
            manager.addPacketListener(object : PacketAdapter(Main.instance!!, PacketType.Play.Client.CHAT){
                override fun onPacketReceiving(event: PacketEvent?) {
                    event?.let {
                        val packet:PacketContainer? = event.packet
                        val player = MsmpPlayer(event.player)
                        packet?.let {
                            val stringMessage = packet.strings.read(0)
                            stringMessage?.let {
                                if (player.chatColors || player.player.isOp || player.permissionLevel >= Ranks.HELPER.permissionLevel){
                                    val newMessage: String = Chat().translate("${player.rank.displayName}&r ${player.chatNamePrefix} ${player.name} ⏵ $stringMessage")
                                    event.isCancelled = true
                                    for (p in Bukkit.getOnlinePlayers()){
                                        if (!Main.playerData.hasPlayerBlocked(p.name, player.name)) {
                                                p.sendMessage(newMessage)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            })
        }
    }

}