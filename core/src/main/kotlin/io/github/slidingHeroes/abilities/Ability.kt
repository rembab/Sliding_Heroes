package io.github.slidingHeroes.abilities

import io.github.slidingHeroes.server.Updatable
import io.github.slidingHeroes.units.Unit

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
