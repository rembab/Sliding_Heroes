package io.github.slidingHeroes.vfx

import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.server.rendering.Drawable
import io.github.slidingHeroes.server.Updatable

/**
 * a visual effect that is temporary and disappears after a set duration
 */
abstract class TempVfx(val position : Vector2, val duration : Float) : Updatable, Drawable {
    var timer : Float = duration
    val timeRatio : Float
        get() = if (duration == 0f) 0f else timer / duration


    init {
        if(duration <= 0f) println("Timed vfx with non positive duration")
        else
        {
            startUpdate()
            show()
        }
    }

    override fun update(deltaTime: Float) {
        timer -= deltaTime
        if (timer <= 0f)
        {
            stopUpdate()
            hide()
        }
    }
}
