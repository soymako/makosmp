package me.soymako.makosmp.Events

import MsmpInventory
import me.soymako.makosmp.Custom.MsmpPlayer
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class InventoryClicked(var player:MsmpPlayer, var itemStack: ItemStack?, var msmpInventory: MsmpInventory?, var mcEvent:InventoryClickEvent?) : Event() {

    companion object{
        @JvmStatic
        var handlerList = HandlerList()
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }


}