package me.soymako.makosmp.Events

import me.soymako.makosmp.Custom.MsmpPlayer
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class PlayerRegistered(var player:MsmpPlayer, var password:String) : Event() {
    companion object{
        @JvmStatic
        val handlerList = HandlerList()
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }



}