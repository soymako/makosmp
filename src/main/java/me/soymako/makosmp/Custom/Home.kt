package me.soymako.makosmp.Custom

import org.bukkit.Location
import org.bukkit.configuration.serialization.ConfigurationSerializable

data class Home(var name: String, var location: Location) : ConfigurationSerializable {

    override fun serialize(): MutableMap<String, Any> {
        return mutableMapOf(
            "name" to name,
            "location" to location
        )
    }

    companion object {
        @JvmStatic
        fun deserialize(args: Map<String, Any>): Home {
            val name = args["name"] as String
            val location = args["location"] as Location
            return Home(name, location)
        }
    }
}
