package io.github.slidingHeroes.server.rendering

/**
 * anything that can be drawn and made visible on the screen
 */
interface Drawable{
    fun draw(rend: Renderer)
    fun show(zorder: Int = 0)
    {
        DrawingBus.add(this, zorder)
    }
    fun hide()
    {
        DrawingBus.remove(this)
    }
}
