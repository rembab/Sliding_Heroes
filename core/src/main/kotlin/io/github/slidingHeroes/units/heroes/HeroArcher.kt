package io.github.slidingHeroes.units.heroes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.entities.projectiles.ArcherArrow
import io.github.slidingHeroes.mobile.Joystick
import io.github.slidingHeroes.server.HeroesModule
import io.github.slidingHeroes.units.UnitStatus
import io.github.slidingHeroes.server.LevelSpace
import io.github.slidingHeroes.units.enemies.Enemy
import io.github.slidingHeroes.util.GyroMessage
import io.github.slidingHeroes.util.JoyStickReleasedMessage
import io.github.slidingHeroes.util.JoystickDraggedMessage
import io.github.slidingHeroes.util.Physics
import io.github.slidingHeroes.util.PlayerInputMessage

class HeroArcher (levelSpace : LevelSpace, heroesModule: HeroesModule, ownerID: Int) :
    Hero(levelSpace, heroesModule, ownerID
) {
    override val status: UnitStatus = UnitStatus(50f)
    private var aimDirection : Vector2? = null

    private val shootCd = 1f
    private var shootTimer = 0f

    override fun update(deltaTime: Float)
    {
        super.update(deltaTime)
        shootTimer-=deltaTime
        if (shootTimer > 0f) aimDirection = null

    }
    fun fireArrow()
    {
        aimDirection?.let {
            if (shootTimer < 0){
                ArcherArrow(position.cpy(), it, levelSpace)
                shootTimer = shootCd
            }
        }
    }

    fun aim(x:Float, y:Float)
    {
        if (shootTimer < 0f && (x != 0f || y != 0f)) aimDirection = Vector2(x, y)
        else aimDirection = null
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
            aim(inp.x,inp.y)
        }
        if (inp is JoyStickReleasedMessage)
        {
            fireArrow()
            aimDirection = null
        }
    }
}

