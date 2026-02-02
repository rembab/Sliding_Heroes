package io.github.slidingHeroes.server.world

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.util.GyroMessage
import io.github.slidingHeroes.util.PlayerInput

class Hero(val scene: LevelScene) {
    companion object{
        val baseSpeed: Float = 1000f
        val deacceleration: Float = 0.95f
        val maxSpeed: Float = 500f

        val size = 20f
    }
    val speed : Float = 1f
    var position: Vector2 = Vector2()
    var velocity: Vector2 = Vector2()
    private var gyro = GyroMessage()

    fun draw(shape : ShapeRenderer) {
        val halfsize = size * 0.5f
        shape.color = Color.RED
        shape.rect(position.x-halfsize, position.y - halfsize, size, size )
    }

    fun update(deltaTime: Float) {
       move(deltaTime)
    }

    private fun move(deltaTime: Float) {
        velocity.add(Vector2(gyro.x * deltaTime, gyro.y * deltaTime))
        velocity.scl(deacceleration)
        velocity.limit(maxSpeed/(speed*baseSpeed))
        position.add(velocity.x * deltaTime *baseSpeed*speed, velocity.y * deltaTime*baseSpeed*speed)
        scene.keepInBounds(position, size/2)
    }

    fun recieveInput(inp : PlayerInput)
    {
        if (inp is GyroMessage)
            gyro = inp
    }
}
