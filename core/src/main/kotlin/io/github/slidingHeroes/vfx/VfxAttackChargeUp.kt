package io.github.slidingHeroes.vfx

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.sun.source.util.SourcePositions
import io.github.slidingHeroes.server.rendering.Renderer
import io.github.slidingHeroes.server.rendering.outlineCircle

class VfxAttackChargeUp(position: Vector2,
                        duration : Float = 0.5f,
                        val color : Color = Color.WHITE,
                        val size : Float = 45f) : TempVfx(position, duration) {

    override fun draw(rend: Renderer) {
        rend.shape.color = color
        rend.shape.outlineCircle(
            position.x, position.y,
            size * timeRatio * 0.5f,
            5f*timeRatio)
    }


}
