package me.soymako.makosmp.Listeners

import me.soymako.makosmp.Custom.MsmpPlayer
import me.soymako.makosmp.Custom.MsmpInventory
import me.soymako.makosmp.Events.InventoryClicked
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class InventoryListener : Listener {

    @EventHandler
    fun onInventoryClicked(e:InventoryClicked){
        var player:MsmpPlayer = e.player
        var inv: MsmpInventory = e.msmpInventory
        var event = e.mcEvent
    }



}