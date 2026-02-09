package io.github.slidingHeroes.entities.projectiles

import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.onHitEffects.OnHitEffect
import io.github.slidingHeroes.units.Unit
import io.github.slidingHeroes.units.heroes.Hero
import io.github.slidingHeroes.util.BodyTag
import io.github.slidingHeroes.util.RigidBody
import kotlin.collections.forEach

abstract class EnemyProjectile(pos: Vector2, direction: Vector2, speed : Float, val unit: Unit, val onHits : ArrayList<OnHitEffect>) :
    Projectile(pos, direction, unit.levelSpace, speed, arrayListOf(BodyTag.HERO)){
    override fun hit(body: RigidBody) {
        if (body !is Hero) return
        onHits.forEach { it.applyEffect(unit, body) }
    }

}
