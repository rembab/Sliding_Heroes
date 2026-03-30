package io.github.slidingHeroes.server.rendering

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
