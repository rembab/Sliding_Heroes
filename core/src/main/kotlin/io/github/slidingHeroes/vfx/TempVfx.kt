package io.github.slidingHeroes.vfx

import io.github.slidingHeroes.server.rendering.Drawable
import io.github.slidingHeroes.server.Updatable

abstract class TempVfx(val duration : Float) : Updatable, Drawable {
    var timer  = 0f
    val timeRatio
        get() = if (duration == 0f) 0 else timer / duration


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
