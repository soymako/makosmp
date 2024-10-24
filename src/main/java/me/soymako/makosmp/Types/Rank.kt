package me.soymako.makosmp.Types

import org.bukkit.configuration.serialization.ConfigurationSerializable

enum class Ranks(val permissionLevel: Int, val displayName: String) {
    USUARIO(0, "&8&l[Usuario]"),
    DONADOR(1, "&3&l[Donador]"),
    MVP(2, "&d&l[MVP]"),
    HELPER(10, "&5&l[Ayudante]"),
    MOD(10, "&9&l[MODERADOR]"),
    ADMIN(11, "&a&l[ADMIN]"),
    BUILDER(12, "&6&l[&e&lBUILDER&6&l]"),
    DEVELOPER(13, "&5&l[&6&lProgramador&5&l]"),
    OWNER(14, "&c&l[&d&lFundador&c&l]"),
    OP(15, "&d&l&ks&r&d&l[&6&lOP&l&d&l]&k&ls");

    companion object {
        @JvmStatic
        fun fromPermissionLevel(permissionLevel: Int): Ranks {
            return values().firstOrNull { it.permissionLevel == permissionLevel }
                ?: throw IllegalArgumentException("Invalid permission level: $permissionLevel")
        }
    }
}

class Rank(val name: String, val permissionLevel: Int, val displayName: String) : ConfigurationSerializable {
    override fun serialize(): MutableMap<String, Any> {
        return mutableMapOf(
            "name" to name,
            "permission_level" to permissionLevel,
            "display_name" to displayName
        )
    }

    companion object {
        @JvmStatic
        fun deserialize(args: Map<String, Any>): Rank {
            val rango = args["name"] as String  // Convertir el nombre de nuevo al enum
            val permissionLevel = args["permission_level"] as Int
            val displayName = args["display_name"] as String
            return Rank(rango, permissionLevel, displayName)
        }
    }
}
