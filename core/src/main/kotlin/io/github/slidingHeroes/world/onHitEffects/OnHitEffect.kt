package io.github.slidingHeroes.world.onHitEffects

import io.github.slidingHeroes.world.units.Unit

/**
 * on hit effect executing on a target
 * the unit argument is there so for example:
 * there can be a vfx easily added (player shooting a missile at enemy)
 * the unit's stats and buffs can be referenced (may be a bad design choice, will see)
 */
interface OnHitEffect {
    fun applyEffect(unit: Unit, target: Unit)
}
