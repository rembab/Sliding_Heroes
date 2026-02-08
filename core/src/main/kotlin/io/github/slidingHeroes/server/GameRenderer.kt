package io.github.slidingHeroes.server

import com.badlogic.gdx.graphics.glutils.ShapeRenderer

interface Drawable{
    fun draw(shape: ShapeRenderer)
    fun show(zorder: Int = 0)
    {
        GameRenderer.add(this, zorder)
    }
    fun hide()
    {
        GameRenderer.remove(this)
    }
}

object GameRenderer {
    private val observers = HashMap<Drawable, Int>()

    fun add(drawable: Drawable, zorder : Int = 0) {observers[drawable] = zorder}

    fun remove(drawable: Drawable) {observers.remove(drawable)}

    fun draw(shape: ShapeRenderer) {
        val queue = observers.entries.sortedBy { it.value }.map { it.key }
        for (drawable in queue) {drawable.draw(shape)}
    }
}
