package me.soymako.makosmp.Data

import me.soymako.makosmp.Main
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException

class ServerData {

    var file:File? = null
    var data:YamlConfiguration? = null

    fun init(){
        var mainInstance:Main? = Main.instance ?: throw IllegalArgumentException("Main instance es NULO")
        file = File(mainInstance!!.dataFolder, "server_data.yml")

        data = YamlConfiguration.loadConfiguration(file!!)
        saveData()
    }

    fun saveData(){
        if (data == null || file == null) return
        try {
            data!!.save(file!!)
        }catch (e:IOException){
            println("data or file is null: ${e.message}")
        }
    }

//    methods

    var welcomeMessage: String
        get(){
            return data?.getString("welcome_message") ?: "bienvenido";
        }
        set(v){
            data?.set("welcome_message", v)
            saveData()
        }
}