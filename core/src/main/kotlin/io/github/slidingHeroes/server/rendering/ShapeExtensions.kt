package io.github.slidingHeroes.server.rendering

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import kotlin.div
import kotlin.math.PI
import kotlin.random.Random

object Shapes
{
    fun circle(origin: Vector2, radius: Float, segments: Int = 20): ArrayList<Vector2>
    {
        val radius = Vector2.One.cpy().scl(radius)
        var next : Vector2
        val stepAngle : Float = PI.toFloat()*2 / (segments-1).toFloat()
        val result = arrayListOf<Vector2>()
        for (i in 0..segments)
        {
            next = origin.cpy().add(radius.rotateRad(stepAngle))
            result.add(next)
        }
        result.add(result[0])
        return result
    }

}
fun ShapeRenderer.outlineCircle(x : Float,
                                y: Float,
                                radius: Float,
                                width: Float,
                                segments: Int = 20,
                                percentage: Float = 1f) {
    val points = Shapes.circle(Vector2(x,y), radius, segments)
    val fullSegments : Int = (percentage * segments).toInt()
    val leftOver : Float = percentage * segments - fullSegments
    var prev = points.last()

    var i = 0
    for (j in 0..<fullSegments)
    {
        i = j
        this.rectLine(prev, points[i], width)
        this.circle(prev.x, prev.y, width/2)
        prev = points[i]
    }
    if(percentage < 1f) {
        i += 1
        val next = prev.cpy().add(points[i].sub(prev).scl(leftOver))
        this.rectLine(next, next, width)
        this.circle(prev.x, prev.y, width / 2)
        this.circle(next.x, next.y, width / 2)
    }

}

