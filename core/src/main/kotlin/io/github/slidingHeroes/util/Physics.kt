package io.github.slidingHeroes.util

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.units.heroes.Hero
import kotlin.math.sqrt

abstract class RigidBody
{

    var position: Vector2 = Vector2(0f,0f)
        set(value) {
            val prev = field.cpy()
            field = value
            Physics.updateBody(this, prev)
        }
    var velocity: Vector2 = Vector2(0f,0f)

    var size: Float = 1f
    init {
        Physics.addBody(this)
    }
}

abstract class RigidWall
{

}

object Physics {
    private val GRID_SIZE = 10f
    private val SEPARATION_SPEED = 4000f
    val bodies = HashMap<Pair<Int, Int>, HashSet<RigidBody>>()


    fun addBody(body: RigidBody) {
        val key = getKey(body.position)
        bodies.getOrPut(key) { HashSet() }.add(body)
    }

    fun removeBody(body: RigidBody) {
        val key = getKey(body.position)
        val bodySet = bodies[key]
        if (bodySet != null) {
            bodySet.remove(body)
            if (bodySet.isEmpty()) bodies.remove(key)
        } else {
            println("Tring to remove body that isn't in grid")
        }
    }

    fun updateBody(body: RigidBody, oldPos: Vector2) {
        val oldKey = getKey(oldPos)
        val newKey = getKey(body.position)

        if (oldKey != newKey) {
            bodies[oldKey]?.remove(body)

            bodies.getOrPut(newKey) { HashSet() }.add(body)
        }
    }

    fun update(deltatime: Float)
    {
        resolveCollisions(deltatime)
        for (body in bodies.values.flatten())
        {
            val oldPos = body.position.cpy()
            body.position.mulAdd(body.velocity, deltatime)
            updateBody(body, oldPos)
        }
    }
    private fun getKey(pos: Vector2): Pair<Int, Int> {
        return Pair(MathUtils.floor(pos.x / GRID_SIZE), MathUtils.floor(pos.y / GRID_SIZE))
    }

    fun allCircle(pos: Vector2, radius: Float): ArrayList<RigidBody> {
        val steps = (radius / GRID_SIZE).toInt()
        val radius2 = radius * radius
        val result = ArrayList<RigidBody>()
        val (centerX, centerY) = getKey(pos)

        for (x in -steps..steps) for (y in -steps..steps) {
            val bodySet = bodies[centerX + x to centerY + y] ?: continue
            for (body in bodySet)
                if (body.position.dst2(pos) < radius2)
                    result.add(body)
        }
        return result
    }

    fun closestCircle(pos: Vector2, radius: Float): RigidBody? {
        return allCircle(pos, radius).minByOrNull { it.position.dst2(pos) }
    }


    //this was clankered
    fun resolveCollisions(deltaTime: Float) {
        val allBodies = bodies.values.flatten().distinct()

        for (bodyA in allBodies) {
            val radiusA = bodyA.size / 2f
            val neighbors = allCircle(bodyA.position, radiusA * 2f)
            for (bodyB in neighbors) {
                if (bodyA == bodyB || bodyA.hashCode() > bodyB.hashCode()) continue

                val radiusB = bodyB.size / 2f
                val collisionNormal = bodyA.position.cpy().sub(bodyB.position)
                val dist2 = collisionNormal.len2()
                val radiusSum = radiusA + radiusB
                if (dist2 > 0 && dist2 < radiusSum * radiusSum) {
                    val dist = sqrt(dist2.toDouble()).toFloat()
                    if (dist != 0f) collisionNormal.scl(1f / dist)
                    else collisionNormal.set(0f, 1f)
                    val penetration = radiusSum - dist
                    val relativeVel = bodyA.velocity.cpy().sub(bodyB.velocity)
                    val velAlongNormal = relativeVel.dot(collisionNormal)
                    if (velAlongNormal < 0) {
                        val restitution = 0.5f
                        val j = -(1 + restitution) * velAlongNormal
                        val impulse = collisionNormal.cpy().scl(j / 2f)
                        bodyA.velocity.add(impulse)
                        bodyB.velocity.sub(impulse)
                    }
                    val pushVelocity = collisionNormal.cpy().scl(penetration * SEPARATION_SPEED)

                    bodyA.velocity.mulAdd(pushVelocity, deltaTime)
                    bodyB.velocity.mulAdd(pushVelocity, -deltaTime)
                }
            }
        }
    }
}
