package io.github.slidingHeroes.units.heroes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.abilities.ProjectileAbility
import io.github.slidingHeroes.entities.projectiles.ArcherArrow
import io.github.slidingHeroes.entities.projectiles.Projectile
import io.github.slidingHeroes.mobile.Joystick
import io.github.slidingHeroes.server.HeroesModule
import io.github.slidingHeroes.units.UnitStatus
import io.github.slidingHeroes.server.LevelSpace
import io.github.slidingHeroes.server.UpdateBus
import io.github.slidingHeroes.units.enemies.Enemy
import io.github.slidingHeroes.util.GyroMessage
import io.github.slidingHeroes.util.JoyStickReleasedMessage
import io.github.slidingHeroes.util.JoystickDraggedMessage
import io.github.slidingHeroes.util.Physics
import io.github.slidingHeroes.util.PlayerInputMessage
import kotlin.reflect.KClass

class HeroArcher (levelSpace : LevelSpace, heroesModule: HeroesModule, ownerID: Int) :
    Hero(levelSpace, heroesModule, ownerID
) {
    override val status: UnitStatus = UnitStatus(50f)

    val arrowAbility: ProjectileAbility =
        ProjectileAbility(
            this,
            0.5f,
            ArcherArrow::class as KClass<Projectile>,
            false)

    override fun draw(shape: ShapeRenderer)
    {
        val halfsize = size * 0.5f
        shape.color = Color.GREEN
        shape.rect(position.x-halfsize, position.y - halfsize, size, size )
        if( arrowAbility.aimDirection!=null)
            shape.line(position, position.cpy().mulAdd(arrowAbility.aimDirection,200f))
    }

    override fun receiveInput(inp: PlayerInputMessage) {
        super.receiveInput(inp)
        if (inp is JoystickDraggedMessage)
        {
            arrowAbility.aim(Vector2(inp.x,inp.y))
        }
        if (inp is JoyStickReleasedMessage)
        {
            arrowAbility.use()
            arrowAbility.aim(null)
        }
    }

    override fun die() {
        super.die()
        UpdateBus.remove(arrowAbility)
    }
}

