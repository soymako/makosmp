package me.soymako.makosmp.Events

import me.soymako.makosmp.Custom.MsmpPlayer
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class ChatColorsObtained(var player:MsmpPlayer, var value:Boolean) : Event() {
    companion object{
        @JvmStatic
        var handlerList = HandlerList()
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }
}