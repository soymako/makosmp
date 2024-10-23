package me.soymako.makosmp.Listeners

import me.soymako.makomc.Chat
import me.soymako.makosmp.Custom.MsmpLivingEntity
import me.soymako.makosmp.Custom.MsmpPlayer
import org.bukkit.Bukkit
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Arrow
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.EntityTargetLivingEntityEvent
import kotlin.random.Random


class EntityListener : Listener {

    @EventHandler
    fun onEntityDamaged(event:EntityDamageByEntityEvent){
        if (event.entity is LivingEntity){
            val entidad = MsmpLivingEntity(event.entity as LivingEntity)
            var player:MsmpPlayer? = null
//            Bukkit.broadcastMessage("causing damage: ${event.damageSource.causingEntity}")

            if (event.damageSource.causingEntity is Player){
                player = MsmpPlayer(event.damageSource.causingEntity as Player)
                if (player.vanish){
                    Chat().alerta(player.player, "No puedes hacer eso mientras usas Vanish", true)
                    event.isCancelled = true
                    return
                }
//                player.sendMessage("hola")
                entidad.addDamage(player, event.damage)
            }

        }
    }

    @EventHandler
    fun onEntityDie(event:EntityDeathEvent){
        var entidad:MsmpLivingEntity = MsmpLivingEntity(event.entity)
        if (entidad.entidad.killer is Player){
            var player:MsmpPlayer = MsmpPlayer(entidad.entidad.killer!!)
            var random = Random(1)
            var dinero:Double = random.nextDouble(1.0, entidad.entidad.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value) + entidad.getDamageDealtByKiller(player)
            if (entidad.entidad is Player){
                var enemigo = MsmpPlayer(entidad.entidad as Player)
                dinero *= enemigo.player.exp
                var dineroRobado = random.nextDouble(200.0, 1000.0)
                player.robarDinero(enemigo, dineroRobado)
            }
            player.addDinero(dinero)
        }
        if (entidad.entidad is Player){
            var p = MsmpPlayer(entidad.entidad as Player)
            val random = Random(3)
            var dineroAPerder = random.nextDouble(1.0, 1000.0)
            p.removeDinero(dineroAPerder)
        }
    }

    @EventHandler
    fun onEntityDamaged(e:EntityDamageEvent){
        if (e.entity is Player){
            val player = MsmpPlayer(e.entity as Player)
            if (player.vanish) e.isCancelled = true
        }
    }

    @EventHandler
    fun onEntityTarget(e:EntityTargetLivingEntityEvent){
        var target = e.target
        var entity = e.entity

        if (target is Player){
            var player = MsmpPlayer(target)
            if (player.vanish) e.isCancelled = true
        }
    }

}