package io.github.slidingHeroes.world.units

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.server.rendering.Drawable
import io.github.slidingHeroes.server.rendering.Renderer

class StatusBar(val unit : Unit) : Drawable {
    init {
        show(1)
    }
    private val width : Float = 20f
    private val height : Float = 4f
    private val offset : Vector2 = Vector2(0f,20f)

    override fun draw(rend: Renderer) {
        val position = offset.cpy().add(unit.position)
        rend.shape.color = Color.WHITE
        rend.shape.rect(
            position.x - width / 2,
            position.y - height / 2,
            width,
            height
        )
        rend.shape.color = Color.RED
        rend.shape.rect(
            position.x - width / 2,
            position.y - height / 2,
            width * unit.status.hpRatio(),
            height
        )
    }
}
