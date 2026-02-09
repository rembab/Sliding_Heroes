package io.github.slidingHeroes.vfx

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import io.github.slidingHeroes.server.rendering.Renderer

class VfxAttackChargeUp(val shape: ShapeRenderer,
                        duration : Float = 1f,
                        val color : Color = Color.WHITE,
                        val size : Float = 1f) : TempVfx(duration) {

    override fun draw(rend: Renderer) {
        shape.color = color

    }


}
