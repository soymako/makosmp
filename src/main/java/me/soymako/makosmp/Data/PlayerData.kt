package me.soymako.makosmp.Data

import me.soymako.makomc.Chat
import me.soymako.makosmp.Custom.MsmpPlayer
import me.soymako.makosmp.Main
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException

class PlayerData {

    var file:File? = null
    var data: YamlConfiguration? = null

    fun init(){
        var mainInstance: Main? = Main.instance ?: throw IllegalArgumentException("Main instance es NULO")
        file = File(mainInstance!!.dataFolder, "player_data.yml")

        data = YamlConfiguration.loadConfiguration(file!!)
        saveData()
    }

    fun saveData(){
        if (data == null || file == null) return
        try {
            data!!.save(file!!)
        }catch (e: IOException){
            println("data or file is null: ${e.message}")
        }
    }

//    methods

    fun setPassword(playerName:String, password: String){
        data?.set("$playerName.password", password)
        saveData()
    }

    fun getPassword(playerName: String): String{
        return data?.getString("$playerName.password") ?: "null"
    }

    fun blockPlayer(player: MsmpPlayer, targetPlayers: MutableList<String>) {
        var blockedPlayers = getBlockedPlayers(player.player.name) ?: mutableListOf()
        val newBlockedPlayers = mutableListOf<String>()

        var messageAgregados: String = "&8Jugadores bloqueados:\n&a"


        targetPlayers.forEach { target ->

            if (target != player.player.name){
                newBlockedPlayers.add(target)
            }
        }

        blockedPlayers.addAll(newBlockedPlayers)
        data?.set("${player.player.name}.bloqueados", newBlockedPlayers)
        saveData()

        newBlockedPlayers.forEach { target -> messageAgregados = messageAgregados.plus("+[$target]\n")  }

        Chat().playerMessage(player.player, messageAgregados)
    }

    fun unblockPlayers(player: MsmpPlayer, targetPlayers: MutableList<String>) {
        var blockedPlayers = getBlockedPlayers(player.player.name) ?: mutableListOf()
        val remainingBlockedPlayers = blockedPlayers.toMutableList() // copia de la lista original
        var quitados = "&8Desbloqueados:\n&c"

        targetPlayers.forEach { target ->
            if (blockedPlayers.contains(target)) {
//                Bukkit.broadcastMessage("tienes bloqueado a: $target")
                remainingBlockedPlayers.remove(target)
                quitados = quitados.plus("-[$target]\n")
            }
        }


        data?.set("${player.player.name}.bloqueados", remainingBlockedPlayers)
        saveData()
        Chat().playerMessage(player.player, quitados)
    }




    fun getBlockedPlayers(playerName:String): MutableList<String>? {
        return data?.getStringList("$playerName.bloqueados")
    }

    fun hasPlayerBlocked(player:String, target:String): Boolean{
        return getBlockedPlayers(player)?.contains(target) ?: false
    }

    fun getDinero(player: MsmpPlayer): Double{
        return data?.getDouble("${player.name}.dinero") ?: .0
    }

    fun setDinero(player: MsmpPlayer, v:Double){
        val valor = if (v < 0) 0.0 else
        data?.set("${player.name}.dinero", v)
        saveData()
    }

    fun addDinero(player:MsmpPlayer, v:Double){
        setDinero(player, getDinero(player) + v)
    }

    fun removeDinero(player:MsmpPlayer, v:Double){
        setDinero(player, getDinero(player) - v)
    }


}

