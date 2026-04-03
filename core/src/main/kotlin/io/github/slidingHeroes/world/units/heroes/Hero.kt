package io.github.slidingHeroes.world.units.heroes

import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.server.HeroesModule
import io.github.slidingHeroes.server.LevelSpace
import io.github.slidingHeroes.world.units.Unit
import io.github.slidingHeroes.util.BodyTag
import io.github.slidingHeroes.util.GyroMessage
import io.github.slidingHeroes.util.PlayerInputMessage

/**
 * player controlled hero class
 */
abstract class Hero(levelSpace: LevelSpace, val heroesModule: HeroesModule, val ownerID : Int
) : Unit(levelSpace) {
    init {
        addTag(BodyTag.HERO)
    }
    private var gyro : GyroMessage = GyroMessage() // gyro input for movement

    override fun move(deltaTime: Float)
    {
        velocity.mulAdd(Vector2(gyro.x, gyro.y), deltaTime*speed*baseSpeed)
        super.move(deltaTime)
    }

    override fun die()
    {
        super.die()
        heroesModule.remove(ownerID)
    }

    open fun receiveInput(inp : PlayerInputMessage)
    {
        if (inp is GyroMessage)
            gyro = inp
    }
}
