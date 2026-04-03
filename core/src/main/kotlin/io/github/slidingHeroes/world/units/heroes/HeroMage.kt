package io.github.slidingHeroes.world.units.heroes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import io.github.slidingHeroes.server.HeroesModule
import io.github.slidingHeroes.world.units.UnitStatus
import io.github.slidingHeroes.server.LevelSpace
import io.github.slidingHeroes.server.rendering.Renderer

/**
 * a hero mage class
 * slow, patient, casting powerful spells from the backline
 * todo: both abilities
 */
class HeroMage (scene : LevelSpace, heroesModule: HeroesModule, ownerID: Int) : Hero(scene, heroesModule, ownerID) {

    override var status : UnitStatus = UnitStatus(75f)

    override fun draw(rend: Renderer)
    {
        val halfsize = size * 0.5f
        rend.shape.color = Color.PURPLE
        rend.shape.rect(position.x-halfsize, position.y - halfsize, size, size )
    }
}
