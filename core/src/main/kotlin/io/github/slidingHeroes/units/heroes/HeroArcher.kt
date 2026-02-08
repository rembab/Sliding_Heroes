package io.github.slidingHeroes.units.heroes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.mobile.Joystick
import io.github.slidingHeroes.units.UnitStatus
import io.github.slidingHeroes.server.LevelSpace
import io.github.slidingHeroes.units.enemies.Enemy
import io.github.slidingHeroes.util.GyroMessage
import io.github.slidingHeroes.util.JoyStickReleasedMessage
import io.github.slidingHeroes.util.JoystickDraggedMessage
import io.github.slidingHeroes.util.Physics
import io.github.slidingHeroes.util.PlayerInputMessage

class HeroArcher (levelSpace : LevelSpace) : Hero(levelSpace) {
    var aimDirection : Vector2? = null

    override val status: UnitStatus = UnitStatus(50f)
    override fun update(deltaTime: Float)
    {
        super.update(deltaTime)
        if (aimDirection != null)
            for (e in Physics.allRay(position, position.cpy().mulAdd(aimDirection, 200f))) {
                if (e is Enemy)
                    e.damage(deltaTime*10f)
            }
    }
    override fun draw(shape: ShapeRenderer)
    {
        val halfsize = size * 0.5f
        shape.color = Color.GREEN
        shape.rect(position.x-halfsize, position.y - halfsize, size, size )
        if( aimDirection != null) shape.line(position, position.cpy().mulAdd(aimDirection,200f))
    }

    override fun receiveInput(inp: PlayerInputMessage) {
        super.receiveInput(inp)
        if (inp is JoystickDraggedMessage)
        {
            if (inp.x != 0f && inp.y != 0f) aimDirection = Vector2(inp.x, inp.y)
            else aimDirection = null
        }
        if (inp is JoyStickReleasedMessage)
        {
            aimDirection = null
        }
    }
}

