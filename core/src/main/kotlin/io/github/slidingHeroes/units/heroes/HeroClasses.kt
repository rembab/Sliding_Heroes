package io.github.slidingHeroes.units.heroes

import kotlin.reflect.KClass

data class HeroRecord(val prefab: KClass<Hero>,
                      val name: String,
                      val description: String)


object SelectableHeroes : ArrayList<HeroRecord>(){
    private fun readResolve(): Any = SelectableHeroes

    init {
        this.add(HeroRecord(
            HeroArcher::class as KClass<Hero>,
            "Archer",
            "Arrow to the knee"))
        this.add(HeroRecord(
            HeroMage::class as KClass<Hero>,
            "Mage",
            "So what do I just say fireball?"))
        this.add(HeroRecord(
            HeroKnight::class as KClass<Hero>,
            "Knight",
            "Parry this you filthy casual"))

    }
}
