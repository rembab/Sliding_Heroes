package io.github.slidingHeroes.units.heroes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import io.github.slidingHeroes.units.UnitStatus
import io.github.slidingHeroes.server.LevelSpace

class HeroMage (scene : LevelSpace) : Hero(scene) {

    override var status : UnitStatus = UnitStatus(75f)

    override fun draw(shape: ShapeRenderer)
    {
        val halfsize = size * 0.5f
        shape.color = Color.PURPLE
        shape.rect(position.x-halfsize, position.y - halfsize, size, size )
    }
}
