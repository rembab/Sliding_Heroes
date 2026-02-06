package io.github.slidingHeroes.units.heroes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import io.github.slidingHeroes.units.UnitStatus
import io.github.slidingHeroes.server.LevelSpace

class HeroKnight (levelSpace : LevelSpace) : Hero(levelSpace) {

    override val status : UnitStatus = UnitStatus(100f)
    override fun draw(shape: ShapeRenderer)
    {
        val halfsize = size * 0.5f
        shape.color = Color.LIGHT_GRAY
        shape.rect(position.x-halfsize, position.y - halfsize, size, size )
    }
}

