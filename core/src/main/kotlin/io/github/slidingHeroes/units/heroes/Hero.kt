package io.github.slidingHeroes.units.heroes

import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.server.LevelSpace
import io.github.slidingHeroes.units.Unit
import io.github.slidingHeroes.util.GyroMessage
import io.github.slidingHeroes.util.PlayerInputMessage

abstract class Hero(levelSpace: LevelSpace) : Unit(levelSpace) {

    var gyro : GyroMessage = GyroMessage()

    override fun move(deltaTime: Float)
    {
        velocity.add(Vector2(gyro.x * deltaTime, gyro.y * deltaTime))
        super.move(deltaTime)
    }

    fun receiveInput(inp : PlayerInputMessage)
    {
        if (inp is GyroMessage)
            gyro = inp
    }
}
