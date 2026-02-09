package io.github.slidingHeroes.onHitEffects

import io.github.slidingHeroes.units.Unit

class OnHitDamage(val dmg: Float) : OnHitEffect{
    override fun applyEffect(unit: Unit, target: Unit) {
        target.damage(dmg)
    }

}
