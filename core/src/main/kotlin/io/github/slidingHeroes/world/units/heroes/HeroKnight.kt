package io.github.slidingHeroes.world.units.heroes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import io.github.slidingHeroes.server.HeroesModule
import io.github.slidingHeroes.world.units.UnitStatus
import io.github.slidingHeroes.server.LevelSpace
import io.github.slidingHeroes.server.rendering.Renderer

/**
 * a hero knight class
 * bulky fighter sticking to the frontline
 * todo: both abilities
 */
class HeroKnight (levelSpace : LevelSpace, heroesModule: HeroesModule, ownerID: Int) : Hero(levelSpace, heroesModule,
    ownerID
) {

    override val status : UnitStatus = UnitStatus(100f)
    override fun draw(rend: Renderer)
    {
        val halfsize = size * 0.5f
        rend.shape.color = Color.LIGHT_GRAY
        rend.shape.rect(position.x-halfsize, position.y - halfsize, size, size )
    }
}

