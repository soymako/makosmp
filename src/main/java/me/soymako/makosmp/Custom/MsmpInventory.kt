package me.soymako.makosmp.Custom

import me.soymako.makosmp.Events.InventoryClicked
import me.soymako.makosmp.Main
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class MsmpInventory(var owner: MsmpPlayer, var size:Int, var name:String, var allowMovement:Boolean) : Listener {

    var inventory = Bukkit.createInventory(owner.player, size, name)

    init {

        Bukkit.getServer().pluginManager.registerEvents(this, Main.instance!!)
    }

    fun open(){
        owner.player.openInventory(inventory)
    }


    @EventHandler
    fun onInventoryClick(e:InventoryClickEvent){
        var inventoryClicked = InventoryClicked(player = owner, itemStack = e.currentItem, this, mcEvent = e)
        Bukkit.getPluginManager().callEvent(inventoryClicked)
        if (e.inventory == this.inventory && !allowMovement){
            e.isCancelled = true
        }
    }

}