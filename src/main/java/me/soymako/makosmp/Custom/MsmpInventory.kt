import me.soymako.makomc.Chat
import me.soymako.makosmp.Custom.MsmpPlayer
import me.soymako.makosmp.Main
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory

class MsmpInventory(
    val owner: MsmpPlayer,
    val size: Int,
    val name: String,
    var allowMovement: Boolean = true, // Por defecto permite movimiento
    var customData: MutableMap<String, Any?> = mutableMapOf() // Aquí puedes meter más datos si lo necesitas
) {

    var inventory: Inventory = Bukkit.createInventory(owner.player, size, Chat().translate(name))

    fun open() {
        Main.inventarios[owner.player] = this
        owner.player.openInventory(inventory)
    }

    fun close() {
        owner.player.closeInventory()
        Main.inventarios.remove(owner.player)
        // Aquí puedes añadir lógica extra si lo necesitas, como limpiar el inventario
    }

    // Método para añadir datos personalizados
    fun addCustomData(key: String, value: Any?) {
        customData[key] = value
    }

    // Método para obtener datos personalizados
    fun getCustomData(key: String): Any? {
        return customData[key]
    }

}
