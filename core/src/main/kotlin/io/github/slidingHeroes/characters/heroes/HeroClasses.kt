package io.github.slidingHeroes.characters.heroes

import io.github.slidingHeroes.server.world.Hero
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
            "Pew pew meow"))
        this.add(HeroRecord(
            HeroMage::class as KClass<Hero>,
            "Mage",
            "So what do I just say fireball?"))

    }
}
