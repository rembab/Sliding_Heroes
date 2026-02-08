package io.github.slidingHeroes.entities.projectiles

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.server.Drawable
import io.github.slidingHeroes.server.LevelSpace
import io.github.slidingHeroes.units.enemies.Enemy
import io.github.slidingHeroes.units.heroes.Hero
import io.github.slidingHeroes.util.BodyTag
import io.github.slidingHeroes.util.RigidBody

class EnemyArrow(
    position: Vector2,
    direction: Vector2,
    levelSpace: LevelSpace
) : Projectile(position, direction, levelSpace, 500f, arrayListOf(BodyTag.HERO)), Drawable {

    private val DAMAGE = 5f

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
