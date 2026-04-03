package io.github.slidingHeroes.server

import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.world.units.enemies.Enemy
import io.github.slidingHeroes.world.units.enemies.EnemyArcher
import kotlin.random.Random

/**
 * module handling the spawning of enemies.
 * for now this is mostly a placeholder that keeps spawning enemies in the middle of the map
 * more complex and engaging combat and spawning enemies will be implemented later in development
 */
class EnemiesModule(val levelSpace: LevelSpace, val heroes: HeroesModule) : Updatable{
    init {
        startUpdate()
    }
    private val enemies : ArrayList<Enemy> = ArrayList()
    private val spawnCd = 2f
    private var spawnTimer = 1f


    fun spawn() {
        val dummy : Enemy = EnemyArcher(levelSpace, heroes)
        dummy.position = levelSpace.middle.cpy().mulAdd(Vector2(Random.nextFloat(), Random.nextFloat()), 10f)
        enemies.add(dummy)
        UnitEventListener.passEvent(dummy, UnitEvent.SPAWNED)
    }

    override fun update(deltaTime: Float) {
        spawnTimer-=deltaTime
        if (spawnTimer <= 0f) {
            spawnTimer = spawnCd
            spawn()
        }

    }

}
