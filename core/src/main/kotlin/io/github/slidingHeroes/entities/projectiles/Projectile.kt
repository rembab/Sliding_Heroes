package io.github.slidingHeroes.entities.projectiles

import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.server.Drawable
import io.github.slidingHeroes.server.LevelSpace
import io.github.slidingHeroes.server.Updatable
import io.github.slidingHeroes.server.UpdateBus
import io.github.slidingHeroes.util.BodyTag
import io.github.slidingHeroes.util.Physics
import io.github.slidingHeroes.util.RigidBody

abstract class Projectile (
    pos: Vector2,
    direction: Vector2,
    val levelSpace: LevelSpace,
    val speed : Float = 40f,
    val hittable : ArrayList<BodyTag>) : RigidBody(), Updatable, Drawable {
    val direction : Vector2 = direction.nor()

    var prevPos : Vector2 = position

    init {
        super.position = pos.cpy()
        overlapping = true
        size = 5f
        show()
        startUpdate()
    }

    override fun update(deltaTime: Float)
    {
        velocity.set(direction.cpy().scl(speed))
        if (!levelSpace.isInBounds(position, 1f))
        {
            destroy()
        }
        Physics.allRay(prevPos, position).forEach { body -> collideWith(body) }
        prevPos = position
    }

    fun destroy() {
        UpdateBus.remove(this)
        Physics.remove(this)
        hide()
    }

    override fun collideWith(other: RigidBody) {
        super.collideWith(other)
        if (other.hasAnyTag(hittable)) {hit(other)}
    }

    abstract fun hit(body : RigidBody)

}
