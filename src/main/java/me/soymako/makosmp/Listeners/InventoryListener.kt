package me.soymako.makosmp.Listeners

import me.soymako.makosmp.Events.InventoryClicked
import me.soymako.makosmp.Main
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

class InventoryListener : Listener {



    @EventHandler
    fun onInventoryClicked(e:InventoryClickEvent){
        val p = e.whoClicked as? Player ?: null

        val inv = Main.inventarios[p]

        inv.let {
//            Bukkit.broadcastMessage("inventario: ${inv?.name}")
            e.isCancelled = !it!!.allowMovement
        }
//        var player:MsmpPlayer = e.player
//        var inv: MsmpInventory = e.msmpInventory
//        var event = e.mcEvent
//
//        inv.victima.let {
//            it!!.player.world.playSound(it!!.player.location, Sound.ENTITY_PLAYER_ATTACK_NODAMAGE, SoundCategory.PLAYERS, 1f,1f)
//            it!!.player.world.playSound(it!!.player.location, Sound.ENTITY_PLAYER_ATTACK_WEAK, SoundCategory.PLAYERS, .5f,.5f)
//        }
    }

    @EventHandler
    fun onInventoryClosed(e:InventoryCloseEvent){
        val p = e.player
        Main.inventarios.remove(p)
    }



}