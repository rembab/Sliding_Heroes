package io.github.slidingHeroes.world.onHitEffects

import io.github.slidingHeroes.world.units.Unit

interface OnHitEffect {
    fun applyEffect(unit: Unit, target: Unit)
}
