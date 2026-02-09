package io.github.slidingHeroes.onHitEffects

import io.github.slidingHeroes.units.Unit

interface OnHitEffect {
    fun applyEffect(unit: Unit, target: Unit)
}
