package me.soymako.makosmp

import MsmpInventory
import me.soymako.makomc.Chat
import me.soymako.makosmp.Custom.MsmpPlayer
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.text.NumberFormat
import java.util.*

class Inventories {

    val format = NumberFormat.getInstance(Locale.US).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }

    fun statsInventory(player:MsmpPlayer, target:MsmpPlayer){
//        var inventario:MsmpInventory = Bukkit.createInventory(player.player, 9*2, "Estadísticas de ${target.fullName}")
        var inventario = MsmpInventory(player, 9*2, "Estadísticas de ${target.fullName}", false)


        var content = arrayOfNulls<ItemStack>(18)
        content[4] = getPlayerHead(player)
        for (i in 0..8){
            var slot = content[i]
            if (slot == null){
                content[i] = darkGrayGlass()
            }
        }

        var dinero:ItemStack = getTag("&eDinero: &a${format.format(target.dinero)}")
        var kills:ItemStack = getTag("&cAsesinatos: &e${target.kills}")
        var deaths:ItemStack = getTag("&cMuertes: &e${target.deaths}")
        var kdr:ItemStack = getTag("&cKDR: &e${target.getKDR()}")

        content[9] = dinero
        content[10] = kills
        content[11] = deaths
        content[12] = kdr

        inventario.inventory!!.contents = content
        inventario.open()
    }

    fun darkGrayGlass(): ItemStack{
        var item:ItemStack =  ItemStack(Material.GRAY_STAINED_GLASS_PANE)
        var meta = item.itemMeta
        meta?.setDisplayName("")
        return item
    }

    fun getPlayerHead(player:MsmpPlayer): ItemStack{
        var item = ItemStack(Material.PLAYER_HEAD)
        var meta = item.itemMeta as SkullMeta
        meta.owner = player.name
        meta.setDisplayName(Chat().translate(player.fullName))
        item.itemMeta = meta
        return item
    }

    fun getTag(name:String): ItemStack{
        var item = ItemStack(Material.NAME_TAG)
        var meta = item.itemMeta
        meta?.setDisplayName(Chat().translate(name))
        item.itemMeta = meta
        return item
    }

}