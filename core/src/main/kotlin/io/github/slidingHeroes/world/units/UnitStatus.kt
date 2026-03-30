package io.github.slidingHeroes.world.units

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.server.Updatable
import kotlin.math.max
import kotlin.math.min

class UnitStatus(var maxHp : Float = 1f) : Updatable {

    init {
        startUpdate()
    }
    var hp : Float = maxHp
        set(value) {
            val value = max(0f,value)
            field = min(value, maxHp)
        }

    var stunTime = 0f
    val stunned : Boolean
        get() = stunTime > 0f

    var rootTime = 0f
    val rooted: Boolean
        get() = stunned || rootTime > 0f
    fun hpRatio() : Float = hp/maxHp

    fun stun(dur : Float) {stunTime = max(stunTime, dur)}
    fun root(dur : Float) {rootTime = max(rootTime, dur)}
    fun damage(dmg : Float) {hp -= dmg}

    override fun update(deltaTime: Float) {
        if (stunned) stunTime -= deltaTime
        if (rooted) rootTime -= deltaTime
    }

}
