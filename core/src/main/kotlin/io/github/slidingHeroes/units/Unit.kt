package io.github.slidingHeroes.units

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.server.LevelSpace
import io.github.slidingHeroes.util.Physics
import io.github.slidingHeroes.util.RigidBody


abstract class Unit(val levelSpace: LevelSpace) : RigidBody() {
    companion object{
        val baseSpeed: Float = 1000f
        val deacceleration: Float = 0.93f
    }
    init {
        size = 20f
    }
    open val status = UnitStatus()
    val speed : Float = 1f

    abstract fun draw(shape : ShapeRenderer)

    fun drawStatus(shape :ShapeRenderer){
        status.drawStatusBar(shape, Vector2(0f,size).add(position))
    }

    fun update(deltaTime: Float) {
        move(deltaTime)
    }

    open fun move(deltaTime: Float) {
        velocity.scl(deacceleration)
        val oldpos = position.cpy()
        levelSpace.keepInBounds(position, size/2)
        Physics.updateBody(this, oldpos)
    }

    fun kill()
    {
        Physics.removeBody(this)
    }

}
