package io.github.slidingHeroes.entities.projectiles

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.onHitEffects.OnHitDamage
import io.github.slidingHeroes.server.Drawable
import io.github.slidingHeroes.units.heroes.Hero
import io.github.slidingHeroes.util.RigidBody
import io.github.slidingHeroes.units.Unit

class EnemyArrow(
    position: Vector2,
    direction: Vector2,
    unit: Unit,
) : EnemyProjectile(position, direction, 500f, unit,
    arrayListOf(OnHitDamage(DAMAGE))), Drawable {

    companion object {private val DAMAGE = 5f}

    override fun hit(body: RigidBody) {
        if (body !is Hero) return
        destroy()
        body.damage(DAMAGE)
    }

    override fun draw(shape: ShapeRenderer) {
        shape.setColor(Color.RED)
        shape.rectLine(position, position.cpy().mulAdd(direction.cpy(), -10f), 2f)
    }


}
