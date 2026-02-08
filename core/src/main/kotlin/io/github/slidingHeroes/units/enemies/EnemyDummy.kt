package io.github.slidingHeroes.units.enemies

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.server.HeroesModule
import io.github.slidingHeroes.server.LevelSpace
import io.github.slidingHeroes.units.UnitStatus
import io.github.slidingHeroes.units.heroes.Hero
import io.github.slidingHeroes.util.RigidBody

class EnemyDummy(levelSpace: LevelSpace, heroes : HeroesModule) : Enemy(levelSpace, heroes) {
    override val status: UnitStatus = UnitStatus(50f)
    private val CONTACT_DAMAGE = 10f
    private val attackCD = 0.3f
    private var attackTimer = 0.3f

    override fun draw(shape: ShapeRenderer)
    {
        val halfsize = size * 0.5f
        shape.color = Color.BLACK
        shape.circle(position.x, position.y, halfsize)
    }

    override fun move(deltaTime: Float) {
        val targetDirection = getTargetDirection()
        if (targetDirection!= null)
            velocity.mulAdd(Vector2(targetDirection.x, targetDirection.y), baseSpeed*speed*deltaTime)
        super.move(deltaTime)
    }

    override fun update(deltaTime: Float) {
        super.update(deltaTime)
        attackTimer-=deltaTime
        resetTarget()
    }

    override fun collideWith(other: RigidBody) {
        super.collideWith(other)
        if (other is Hero && attackTimer < 0f) {
            attackTimer = attackCD
            other.damage(CONTACT_DAMAGE)
        }
    }

}
