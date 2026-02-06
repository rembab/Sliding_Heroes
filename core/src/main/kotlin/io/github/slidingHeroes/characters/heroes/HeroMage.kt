package io.github.slidingHeroes.characters.heroes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import io.github.slidingHeroes.server.world.Hero
import io.github.slidingHeroes.server.world.LevelScene

class HeroMage (scene : LevelScene) : Hero(scene) {

    override fun draw(shape: ShapeRenderer)
    {
        val halfsize = size * 0.5f
        shape.color = Color.PURPLE
        shape.rect(position.x-halfsize, position.y - halfsize, size, size )
    }
}
