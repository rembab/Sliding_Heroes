package io.github.slidingHeroes.units

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.server.LevelSpace
import io.github.slidingHeroes.units.heroes.Hero
import io.github.slidingHeroes.util.GyroMessage


abstract class Unit(val levelSpace: LevelSpace){
    companion object{
        val baseSpeed: Float = 1000f
        val deacceleration: Float = 0.95f
        val maxSpeed: Float = 500f

        val size = 20f
    }
    open val status = UnitStatus()
    val speed : Float = 1f
    var position: Vector2 = Vector2()
    var velocity: Vector2 = Vector2()

    abstract fun draw(shape : ShapeRenderer)

    fun drawStatus(shape :ShapeRenderer){
        status.drawStatusBar(shape, Vector2(0f,size).add(position))
    }

    fun update(deltaTime: Float) {
        move(deltaTime)
    }

    open fun move(deltaTime: Float) {
        velocity.scl(deacceleration)
        velocity.limit(maxSpeed/(speed*baseSpeed))
        position.add(velocity.x * deltaTime *baseSpeed*speed, velocity.y * deltaTime*baseSpeed*speed)
        levelSpace.keepInBounds(position, size/2)
    }

}
