package io.github.slidingHeroes.server.rendering

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import kotlin.math.PI


fun ShapeRenderer.outlineCircle(x : Float, y: Float, radius: Float, width: Float, segments: Int = 10) {
    val origin = Vector2(x, y)
    val radius = Vector2.One.scl(radius)
    var prev = origin.cpy().add(radius)
    var next : Vector2
    val stepAngle : Float = PI.toFloat()*2 / segments.toFloat()
    for (i in 0..segments)
    {
        next = origin.cpy().add(radius.rotateRad(stepAngle*i))
        this.circle(next.x, next.y, width/2)
        this.rectLine(prev, next, width)
    }
}
