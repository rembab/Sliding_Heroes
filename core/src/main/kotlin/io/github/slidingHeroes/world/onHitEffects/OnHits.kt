package io.github.slidingHeroes.world.onHitEffects

import io.github.slidingHeroes.world.units.Unit

/**
 * various on hit effects that can be applied to attacks
 */
class OnHitDamage(val dmg: Float) : OnHitEffect{
    override fun applyEffect(unit: Unit, target: Unit) {
        target.damage(dmg)
    }
}

class OnHitStun(val dur: Float) : OnHitEffect{
    override fun applyEffect(unit: Unit, target: Unit) {
        target.stun(dur)
    }
}
class OnHitRoot(val dur: Float) : OnHitEffect{
    override fun applyEffect(unit: Unit, target: Unit) {
        target.root(dur)
    }
}



