package io.github.slidingHeroes.units.enemies

import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.server.HeroesModule
import io.github.slidingHeroes.server.LevelSpace
import io.github.slidingHeroes.units.Unit
import io.github.slidingHeroes.units.UnitStatus
import io.github.slidingHeroes.units.heroes.Hero
import io.github.slidingHeroes.util.BodyTag

abstract class Enemy(levelSpace: LevelSpace, val heroes: HeroesModule) : Unit(levelSpace) {
    var target: Hero? = null
    fun getTargetDirection() : Vector2? = target?.position?.cpy()?.sub(position)?.nor()


    init {
        resetTarget()
        addTag(BodyTag.ENEMY)
    }
    override fun update(deltaTime: Float) {
        resetTarget()
        super.update(deltaTime)
    }
    fun resetTarget()
    {
        target = heroes.closestTo(position)
    }


}
