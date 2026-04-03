package io.github.slidingHeroes.world.entities.projectiles

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.world.onHitEffects.OnHitDamage
import io.github.slidingHeroes.server.rendering.Drawable
import io.github.slidingHeroes.server.rendering.Renderer
import io.github.slidingHeroes.world.units.enemies.Enemy
import io.github.slidingHeroes.util.RigidBody
import io.github.slidingHeroes.world.units.Unit

/**
 * arrow spawned by the archer class hero. deals damage and applies knockback to enemy
 */
class ArcherArrow(
    position: Vector2,
    direction: Vector2,
    unit : Unit,
    ) : HeroProjectile(position, direction, 1000f, unit, arrayListOf(OnHitDamage(DAMAGE))), Drawable {

    companion object {
        private val DAMAGE = 25f
        private val KNOCKBACK_FORCE = 1000f
    }

    override fun draw(rend: Renderer) {
        rend.shape.setColor(Color.WHITE)
        rend.shape.rectLine(position, position.cpy().mulAdd(direction.cpy(), -30f), 5f)
    }

    override fun hit(body: RigidBody) {
        if (body !is Enemy) return
        super.hit(body)
        destroy()
        body.velocity.mulAdd(direction, KNOCKBACK_FORCE)
    }
}
