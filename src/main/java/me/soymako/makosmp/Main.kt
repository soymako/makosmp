package me.soymako.makosmp

import me.soymako.makosmp.Commands.*
import me.soymako.makosmp.Commands.Payed.Prefijo
import me.soymako.makosmp.Commands.Staff.ChangeWelcomeMessage
import me.soymako.makosmp.Commands.Staff.SetMaxHomes
import me.soymako.makosmp.Commands.Staff.Vanish
import me.soymako.makosmp.Commands.Staff.teleport
import me.soymako.makosmp.Custom.Home
import me.soymako.makosmp.Data.HomeData
import me.soymako.makosmp.Data.PlayerData
import me.soymako.makosmp.Data.ServerData
import me.soymako.makosmp.Listeners.EntityListener
import me.soymako.makosmp.Listeners.InventoryListener
import me.soymako.makosmp.Listeners.PlayerListener
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    companion object{
        var instance:Main? = null

        var serverData:ServerData = ServerData()
        var playerData:PlayerData = PlayerData()
        var homeData: HomeData = HomeData()
    }

    fun registerCustomDataTypes(){
        ConfigurationSerialization.registerClass(Home::class.java, "Home")
    }

    fun initData(){
        serverData.init()
        playerData.init()
        homeData.init()
    }

    fun registerCommands(){
        getCommand("change_welcome_message")?.setExecutor(ChangeWelcomeMessage())
        getCommand("register")?.setExecutor(Register())
        getCommand("login")?.setExecutor(Login())
        getCommand("sethome")?.setExecutor(SetHome())
        getCommand("home")?.setExecutor(HomeCommand())
        getCommand("bloquear")?.setExecutor(Bloquear())
        getCommand("desbloquear")?.setExecutor(Desbloquear())
        getCommand("prefijo")?.setExecutor(Prefijo())
        getCommand("set-max-homes")?.setExecutor(SetMaxHomes())
        getCommand("dinero")?.setExecutor(Dinero())
        getCommand("estadisticas")?.setExecutor(Estadisticas())
        getCommand("vanish")?.setExecutor(Vanish())
        getCommand("teleport")?.setExecutor(teleport())
    }

    fun registerListeners(){
        server.pluginManager.registerEvents(PlayerListener(), this)
        server.pluginManager.registerEvents(InventoryListener(), this)
        server.pluginManager.registerEvents(EntityListener(), this)

    }

    override fun onEnable() {
        instance = this
        registerCustomDataTypes()
        initData()
        registerCommands()
        registerListeners()

        Timers().tickTimer()
    }

    override fun onDisable() {
        instance = null
    }
}
