package io.github.slidingHeroes.server

import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.units.enemies.Enemy
import io.github.slidingHeroes.units.enemies.EnemyDummy
import kotlin.random.Random

class EnemiesModule(val levelSpace: LevelSpace, val heroes: HeroesModule) : Updatable{
    init {
        UpdateBus.add(this)
    }
    private val enemies : ArrayList<Enemy> = ArrayList()
    private val spawnCd = 2f
    private var spawnTimer = 1f


    fun spawn() {
        val dummy : Enemy = EnemyDummy(levelSpace, heroes)
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
