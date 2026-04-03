package io.github.slidingHeroes.world.entities.projectiles

import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.world.onHitEffects.OnHitEffect
import io.github.slidingHeroes.world.units.Unit
import io.github.slidingHeroes.world.units.heroes.Hero
import io.github.slidingHeroes.util.BodyTag
import io.github.slidingHeroes.util.RigidBody
import kotlin.collections.forEach

/**
 * a projectile originating from an enemy and hitting a hero
 */
abstract class EnemyProjectile(pos: Vector2, direction: Vector2, speed : Float, val unit: Unit, val onHits : ArrayList<OnHitEffect>) :
    Projectile(pos, direction, unit.levelSpace, speed, arrayListOf(BodyTag.HERO)){
    override fun hit(body: RigidBody) {
        if (body !is Hero) return
        onHits.forEach { it.applyEffect(unit, body) }
    }

}
