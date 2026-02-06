package io.github.slidingHeroes.server

import com.badlogic.gdx.math.Vector2
import kotlin.math.max
import kotlin.math.min

class LevelSpace(val bottomLeft: Vector2, val topRight: Vector2) {
    val middle : Vector2 get() {
        return Vector2(bottomLeft.x + topRight.x, topRight.y + bottomLeft.y).scl(0.5f)
    }

    fun isInBounds(position: Vector2, radius: Float) : Boolean {
        return (position.x - radius > bottomLeft.x &&
            position.x + radius < topRight.x &&
            position.y - radius > bottomLeft.y &&
            position.y + radius < topRight.y)
    }
    fun keepInBounds(position: Vector2, radius: Float){
        position.x = max(bottomLeft.x + radius, position.x)
        position.x = min(topRight.x - radius, position.x)
        position.y = max(bottomLeft.y + radius, position.y)
        position.y = min(topRight.y - radius, position.y)
    }


}
