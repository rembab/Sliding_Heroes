package io.github.slidingHeroes.entities.projectiles

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.onHitEffects.OnHitDamage
import io.github.slidingHeroes.server.Drawable
import io.github.slidingHeroes.units.enemies.Enemy
import io.github.slidingHeroes.util.RigidBody
import io.github.slidingHeroes.units.Unit


class ArcherArrow(
    position: Vector2,
    direction: Vector2,
    unit : Unit,
    ) : HeroProjectile(position, direction, 1000f, unit, arrayListOf(OnHitDamage(DAMAGE))), Drawable {

    companion object {
        private val DAMAGE = 25f
        private val KNOCKBACK_FORCE = 1000f
    }

    override fun draw(shape: ShapeRenderer) {
        shape.setColor(Color.WHITE)
        shape.rectLine(position, position.cpy().mulAdd(direction.cpy(), -30f), 5f)
    }

    override fun hit(body: RigidBody) {
        if (body !is Enemy) return
        super.hit(body)
        destroy()
        body.velocity.mulAdd(direction, KNOCKBACK_FORCE)
    }
}
