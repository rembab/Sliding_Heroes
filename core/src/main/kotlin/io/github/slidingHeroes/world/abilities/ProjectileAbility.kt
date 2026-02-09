package io.github.slidingHeroes.world.abilities

import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.world.entities.projectiles.Projectile
import io.github.slidingHeroes.world.units.Unit
import kotlin.reflect.KClass

class ProjectileAbility(unit : Unit, cooldown: Float, val proj: KClass<Projectile>, val automatic: Boolean = false)
    : Ability(unit, cooldown) {

    var aimDirection : Vector2? = null
        private set


    override fun canUse() : Boolean {
        return super.canUse() && aimDirection != null
    }

    override fun onUse() {
        proj.constructors.first().call (unit.position.cpy(), aimDirection!!, unit)
        cdTimer = cooldown
        aimDirection = null
    }

    fun aim(direction : Vector2?)
    {
        if (direction == null) aimDirection = direction
        if (cdTimer < 0f)
        {
            aimDirection = direction?.cpy()
            if (automatic) use()
        }
        else aimDirection = null
    }


}
