package io.github.slidingHeroes.units

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import kotlin.math.max
import kotlin.math.min

class UnitStatus(var maxHp : Float = 1f) {
    val width : Float = 20f
    val height : Float = 4f
    var hp : Float = maxHp
        set(value) {
            val value = max(0f,value)
            field = min(value, maxHp)
        }

    fun hpRatio() : Float = hp/maxHp

    fun drawStatusBar(shape: ShapeRenderer, position: Vector2)
    {
        shape.color = Color.WHITE
        shape.rect(
            position.x - width/2,
            position.y - height/2,
            width,
            height)
        shape.color = Color.RED
        shape.rect(
            position.x - width/2,
            position.y - height/2,
            width*hpRatio(),
            height)
    }
}
