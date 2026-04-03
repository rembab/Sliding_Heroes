package io.github.slidingHeroes.mobile.controls

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.esotericsoftware.kryonet.Client
/**
 * interface elements on mobile that allow for controlling the player character and sending messages through the network.
 */
abstract class MobileControl(val client : Client, val inputId : Int, val minX: Float?, val maxX: Float?) : InputAdapter() {
    abstract fun draw(shape: ShapeRenderer)
}
