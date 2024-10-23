package me.soymako.makosmp.Custom

import me.soymako.makosmp.Main
import org.bukkit.NamespacedKey
import org.bukkit.entity.LivingEntity
import org.bukkit.persistence.PersistentDataType

class MsmpLivingEntity(val entidad:LivingEntity) {

    val dc = entidad.persistentDataContainer

    fun addDamage(damager: MsmpPlayer, damage:Double){
        dc.set(NamespacedKey(Main.instance!!, "${damager.name}"), PersistentDataType.DOUBLE, damage)
    }

    fun getDamageDealtByKiller(killer:MsmpPlayer): Double{
        return dc.get(NamespacedKey(Main.instance!!, "${killer.name}"), PersistentDataType.DOUBLE) ?: 0.0
    }

}