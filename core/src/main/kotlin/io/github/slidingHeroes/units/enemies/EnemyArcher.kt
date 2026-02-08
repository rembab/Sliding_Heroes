package io.github.slidingHeroes.units.enemies

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.abilities.ProjectileAbility
import io.github.slidingHeroes.entities.projectiles.ArcherArrow
import io.github.slidingHeroes.entities.projectiles.EnemyArrow
import io.github.slidingHeroes.entities.projectiles.Projectile
import io.github.slidingHeroes.server.HeroesModule
import io.github.slidingHeroes.server.LevelSpace
import io.github.slidingHeroes.units.UnitStatus
import kotlin.math.abs
import kotlin.reflect.KClass

class EnemyArcher(levelSpace: LevelSpace, heroes: HeroesModule) : Enemy(levelSpace, heroes) {
    private val DESIRED_DISTANCE = 300f
    private val DISTANCE_MARGIN = 50f

    override val status: UnitStatus = UnitStatus(50f)


    val arrowAbility: ProjectileAbility =
        ProjectileAbility(
            this,
            0.5f,
            EnemyArrow::class as KClass<Projectile>,
            true)

    override fun update(deltaTime: Float) {
        arrowAbility.aim(getTargetDirection())
        super.update(deltaTime)
    }

    override fun move(deltaTime: Float) {
        val targetDirection = getTargetDirection()
        if (targetDirection!= null) {
            val targetDistance = position.dst(target!!.position)
            if (abs(DESIRED_DISTANCE - targetDistance) <= DISTANCE_MARGIN)
                velocity.scl(0.9f)
            if (targetDistance < DESIRED_DISTANCE) targetDirection.scl(-1f)
            velocity.mulAdd(Vector2(targetDirection.x, targetDirection.y), baseSpeed * speed * deltaTime)
        }
        super.move(deltaTime)
    }

    override fun draw(shape: ShapeRenderer)
    {
        val halfsize = size * 0.5f
        shape.color = Color.BLACK
        shape.circle(position.x, position.y, halfsize)

        val lookdir : Vector2 = if(getTargetDirection() != null) getTargetDirection()!! else Vector2.One
        val tmp = position.cpy().mulAdd(lookdir,20f)

        shape.rectLine(
            position.cpy().mulAdd(lookdir.cpy().rotateDeg(-45f), 20f),
            tmp,
            5f)

        shape.rectLine(
            position.cpy().mulAdd(lookdir.cpy().rotateDeg(45f), 20f),
            tmp,
            5f)

        shape.circle(tmp.x, tmp.y, 2.5f)
    }

}
