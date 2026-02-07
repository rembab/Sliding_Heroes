package io.github.slidingHeroes.units.enemies

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.server.HeroesController
import io.github.slidingHeroes.server.LevelSpace

class EnemyDummy(levelSpace: LevelSpace, heroes : HeroesController) : Enemy(levelSpace, heroes) {
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

}
