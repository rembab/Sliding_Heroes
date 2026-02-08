package io.github.slidingHeroes.units

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import kotlin.math.max
import kotlin.math.min

class UnitStatus(var maxHp : Float = 1f) {

    var hp : Float = maxHp
        set(value) {
            val value = max(0f,value)
            field = min(value, maxHp)
        }

    fun hpRatio() : Float = hp/maxHp

}
