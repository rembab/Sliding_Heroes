package io.github.slidingHeroes.mobile.controls

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.esotericsoftware.kryonet.Client

abstract class MobileControl(val client : Client, val inputId : Int, val minX: Float?, val maxX: Float?) : InputAdapter() {
    abstract fun draw(shape: ShapeRenderer)
}
