package io.github.slidingHeroes.units.enemies

import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.server.HeroesController
import io.github.slidingHeroes.server.LevelSpace
import io.github.slidingHeroes.units.Unit
import io.github.slidingHeroes.units.heroes.Hero

abstract class Enemy(levelSpace: LevelSpace, val heroes: HeroesController) : Unit(levelSpace) {
    var target: Hero? = null
    fun getTargetDirection() : Vector2? = target?.position?.cpy()?.sub(position)?.nor()


    init {
        resetTarget()
    }
    fun resetTarget()
    {
        target = heroes.closestTo(position)
    }


}
