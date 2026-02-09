package io.github.slidingHeroes.world.units.heroes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.world.abilities.ProjectileAbility
import io.github.slidingHeroes.world.entities.projectiles.ArcherArrow
import io.github.slidingHeroes.world.entities.projectiles.Projectile
import io.github.slidingHeroes.server.HeroesModule
import io.github.slidingHeroes.world.units.UnitStatus
import io.github.slidingHeroes.server.LevelSpace
import io.github.slidingHeroes.server.UpdateBus
import io.github.slidingHeroes.server.rendering.Renderer
import io.github.slidingHeroes.util.ButtonMessage
import io.github.slidingHeroes.util.JoystickDraggedMessage
import io.github.slidingHeroes.util.JoystickMessage
import io.github.slidingHeroes.util.PlayerInputMessage
import kotlin.reflect.KClass

class HeroArcher (levelSpace : LevelSpace, heroesModule: HeroesModule, ownerID: Int) :
    Hero(levelSpace, heroesModule, ownerID
) {
    override val status: UnitStatus = UnitStatus(50f)

    var testcolor = Color.GREEN
    val arrowAbility: ProjectileAbility =
        ProjectileAbility(
            this,
            0.5f,
            ArcherArrow::class as KClass<Projectile>,
            false)

    override fun draw(rend: Renderer)
    {
        val halfsize = size * 0.5f
        rend.shape.color = testcolor
        rend.shape.rect(position.x-halfsize, position.y - halfsize, size, size )
        if( arrowAbility.aimDirection!=null)
            rend.shape.line(position, position.cpy().mulAdd(arrowAbility.aimDirection,200f))
    }

    override fun receiveInput(inp: PlayerInputMessage) {
        super.receiveInput(inp)
        if (inp is JoystickDraggedMessage)
        {
            arrowAbility.aim(Vector2(inp.x,inp.y))
        }
        if (inp is JoystickMessage && !inp.pressed)
        {
            arrowAbility.use()
            arrowAbility.aim(null)
        }
        if (inp is ButtonMessage && inp.pressed)
        {
            testcolor = Color.BLUE
        }
        if (inp is ButtonMessage && !inp.pressed)
        {
            testcolor = Color.GREEN
        }
    }

    override fun die() {
        super.die()
        UpdateBus.remove(arrowAbility)
    }
}

