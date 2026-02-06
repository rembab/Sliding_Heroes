package io.github.slidingHeroes.units.heroes

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.units.UnitStatus
import io.github.slidingHeroes.server.LevelSpace
import io.github.slidingHeroes.units.Unit
import io.github.slidingHeroes.units.enemies.Enemy
import io.github.slidingHeroes.util.GyroMessage
import io.github.slidingHeroes.util.PlayerInput

abstract class Hero(levelSpace: LevelSpace) : Unit(levelSpace) {

    var gyro : GyroMessage = GyroMessage()

    override fun move(deltaTime: Float)
    {
        velocity.add(Vector2(gyro.x * deltaTime, gyro.y * deltaTime))
        super.move(deltaTime)
    }

    fun recieveInput(inp : PlayerInput)
    {
        if (inp is GyroMessage)
            gyro = inp
    }
}
