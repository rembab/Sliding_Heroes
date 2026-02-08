package io.github.slidingHeroes.entities.projectiles

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.server.Drawable
import io.github.slidingHeroes.server.LevelSpace
import io.github.slidingHeroes.units.enemies.Enemy
import io.github.slidingHeroes.util.BodyTag
import io.github.slidingHeroes.util.RigidBody


class ArcherArrow(
    position: Vector2,
    direction: Vector2,
    levelSpace: LevelSpace
    ) : Projectile(position, direction, 1000f, levelSpace, arrayListOf(BodyTag.ENEMY)), Drawable {

    private val KNOCKBACK_FORCE = 1000f
    private val DAMAGE = 25f

    override fun draw(shape: ShapeRenderer) {
        shape.setColor(Color.WHITE)
        shape.rectLine(position, position.cpy().mulAdd(direction.cpy(), -30f), 5f)
    }

    override fun hit(body: RigidBody) {
        if (body !is Enemy) return
        destroy()
        body.damage(DAMAGE)
        body.velocity.mulAdd(direction, KNOCKBACK_FORCE)

    }
}
