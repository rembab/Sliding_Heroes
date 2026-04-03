package io.github.slidingHeroes.server.rendering

import com.badlogic.gdx.graphics.glutils.ShapeRenderer

/**
 * draws all subscribed objects every frame. Using the specified renderer
 */
object DrawingBus {
    private val observers = HashMap<Drawable, Int>()

    fun add(drawable: Drawable, zorder : Int = 0) {observers[drawable] = zorder}

    fun remove(drawable: Drawable) {observers.remove(drawable)}

    fun draw(rend: Renderer) {
        val queue = observers.entries.sortedBy { it.value }.map { it.key }
        for (drawable in queue) {drawable.draw(rend)}
    }
}
