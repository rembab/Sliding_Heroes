package io.github.slidingHeroes.units

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import io.github.slidingHeroes.server.Drawable
import io.github.slidingHeroes.server.LevelSpace
import io.github.slidingHeroes.server.UnitEvent
import io.github.slidingHeroes.server.UnitEventListener
import io.github.slidingHeroes.util.BodyTag
import io.github.slidingHeroes.util.Physics
import io.github.slidingHeroes.util.RigidBody


abstract class Unit(val levelSpace: LevelSpace, ) : RigidBody(), Drawable {
    companion object{
        val baseSpeed: Float = 1000f
        val deacceleration: Float = 0.93f
    }
    init {
        size = 20f
        addTag(BodyTag.UNIT)
        show()
    }
    abstract val status : UnitStatus
    val statusBar : StatusBar = StatusBar(this)
    val speed : Float = 1f

    abstract override fun draw(shape : ShapeRenderer)

    open fun update(deltaTime: Float) {
        move(deltaTime)
        if (status.hp <= 0) die()

    }

    open fun move(deltaTime: Float) {
        velocity.scl(deacceleration)
        val oldpos = position.cpy()
        levelSpace.keepInBounds(position, size/2)
        Physics.updateBody(this, oldpos)
    }

    open fun damage(dmg:Float) {
        status.hp -= dmg
    }

    open fun die()
    {
        Physics.remove(this)
        hide()
        statusBar.hide()
        UnitEventListener.passEvent(this, UnitEvent.DIED)
    }
}
