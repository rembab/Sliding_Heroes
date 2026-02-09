package io.github.slidingHeroes.world.units.heroes

import io.github.slidingHeroes.mobile.controls.BigButton
import io.github.slidingHeroes.mobile.controls.FloatingJoystick
import io.github.slidingHeroes.mobile.controls.MobileControl
import kotlin.reflect.KClass

data class HeroRecord(val prefab: KClass<Hero>,
                      val name: String,
                      val description: String,
                      val controlLeft: KClass<MobileControl>,
                      val controlRight: KClass<MobileControl>)


object SelectableHeroes : ArrayList<HeroRecord>(){
    private fun readResolve(): Any = SelectableHeroes

    init {
        this.add(HeroRecord(
            HeroArcher::class as KClass<Hero>,
            "Archer",
            "Arrow to the knee",
            FloatingJoystick::class as KClass<MobileControl>,
            BigButton::class as KClass<MobileControl>))
        this.add(HeroRecord(
            HeroMage::class as KClass<Hero>,
            "Mage",
            "So what do I just say fireball?",
            FloatingJoystick::class as KClass<MobileControl>,
            FloatingJoystick::class as KClass<MobileControl>))
        this.add(HeroRecord(
            HeroKnight::class as KClass<Hero>,
            "Knight",
            "Parry this you filthy casual",
            FloatingJoystick::class as KClass<MobileControl>,
            FloatingJoystick::class as KClass<MobileControl>))

    }
}
