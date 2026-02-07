package io.github.slidingHeroes.server

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.units.enemies.Enemy
import io.github.slidingHeroes.units.enemies.EnemyDummy
import kotlin.random.Random

class EnemiesController {
    private val enemies : ArrayList<Enemy> = ArrayList()

    fun spawn(levelSpace: LevelSpace, heroes: HeroesController) {
        val dummy : Enemy = EnemyDummy(levelSpace, heroes)
        dummy.position = levelSpace.middle.cpy().mulAdd(Vector2(Random.nextFloat(), Random.nextFloat()), 10f)
        enemies.add(dummy)
    }

    fun draw(shape : ShapeRenderer)
    {
        for(e in enemies) e.draw(shape)
    }

    fun drawStatusBars(shape : ShapeRenderer)
    {
        for(e in enemies) e.drawStatus(shape)
    }

    fun update(deltaTime : Float)
    {
        for (e in enemies) e.resetTarget()
        for (e in enemies) e.update(deltaTime)
    }

}
