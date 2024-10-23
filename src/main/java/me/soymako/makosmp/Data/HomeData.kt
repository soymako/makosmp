package me.soymako.makosmp.Data

import me.soymako.makosmp.Custom.Home
import me.soymako.makosmp.Main
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException

class HomeData {

    var file: File? = null
    var data:YamlConfiguration? = null

    fun init(){
        file = File(Main.instance!!.dataFolder, "home_data.yml")
        data = YamlConfiguration.loadConfiguration(file!!)
    }

    fun saveData(){
        if (file == null || data == null) return

        try{
            data!!.save(file!!)
        }
        catch (e:IOException){
            println("Error intentando guardar home_data.yml, ${e.message}")
        }
    }

//    methods

    fun saveHome(p:String, home:Home){
        data?.set("$p.casas.${home.name}", home)
        saveData()
    }

    fun getHome(p:String, name:String): Home?{
        var cs:ConfigurationSection? = data?.getConfigurationSection("$p.casas")
        if (cs != null){
            var keys:Set<String> = cs.getKeys(false)
            for (k:String in keys){
                if (name == k){
                    var home:Home = data?.getObject("${cs.currentPath}.$k", Home::class.java) as Home
                    return home
                }
            }
        }
        return null
    }

    fun getHomeListString(p:String): MutableList<String>?{
        var cs:ConfigurationSection? = data?.getConfigurationSection("$p.casas")
        if (cs!=null){
            var keys:Set<String> = cs.getKeys(false)
            return keys.toMutableList()
        }


        return null
    }

    fun getHomesAmount(p:String): Int{
        var cs:ConfigurationSection? = data?.getConfigurationSection("$p.casas")
        if (cs!=null){
            var keys:Set<String> = cs.getKeys(false)
            return keys.size
        }
        return 0;
    }

}