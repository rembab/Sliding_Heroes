package io.github.slidingHeroes.world.abilities

import io.github.slidingHeroes.server.Updatable
import io.github.slidingHeroes.world.units.Unit

/**
 * anything that can be considered a unit's ability, that has a cooldown, and that a unit can use
 * upon use can have various effects, cools down over time and can be used again after the cooldown period
 */
abstract class Ability(val unit : Unit, val cooldown: Float) : Updatable {

    init {
        startUpdate()
    }
    var cdTimer = 0f

    fun use()
    {
        if (!canUse()) return
        cdTimer = cooldown
        onUse()
    }

    abstract fun onUse()

    open fun canUse(): Boolean = cdTimer < 0

    override fun update(deltaTime: Float) {
        cdTimer -= deltaTime
    }
}
