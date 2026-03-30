package io.github.slidingHeroes.server.rendering

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

class Renderer {
    val shape : ShapeRenderer = ShapeRenderer()
    val outline : ShapeRenderer = ShapeRenderer()
    val batch : SpriteBatch = SpriteBatch()
    fun begin()
    {
        shape.begin(ShapeRenderer.ShapeType.Filled)
        outline.begin(ShapeRenderer.ShapeType.Line)
        batch.begin()
    }
    fun end()
    {
        shape.end()
        outline.end()
        batch.end()
    }
}
