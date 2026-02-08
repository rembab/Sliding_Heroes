package io.github.slidingHeroes.util

import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.server.Updatable
import io.github.slidingHeroes.server.UpdateBus
import kotlin.math.abs
import kotlin.math.sqrt

object Physics : Updatable {
    init {
        UpdateBus.add(this)
    }
    private val GRID_SIZE = 10f
    private val SEPARATION_SPEED = 4000f
    val bodies = HashMap<Pair<Int, Int>, HashSet<RigidBody>>()
    val toRemove = mutableListOf<RigidBody>()

    fun addBody(body: RigidBody) {
        val key = getKey(body.position)
        bodies.getOrPut(key) { HashSet() }.add(body)
    }

    fun remove(body: RigidBody) {toRemove.add(body)}

    private fun removeBody(body: RigidBody) {
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

    override fun update(deltaTime: Float)
    {
        for (body in toRemove) removeBody(body)
        toRemove.clear()
        resolveCollisions(deltaTime)
        for (body in bodies.values.flatten())
        {
            val oldPos = body.position.cpy()
            body.position.mulAdd(body.velocity, deltaTime)
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

    fun allRay(start: Vector2, end: Vector2): ArrayList<RigidBody> {
        val result = ArrayList<RigidBody>()
        val checkedBodies = HashSet<RigidBody>()

        var x0 = MathUtils.floor(start.x / GRID_SIZE)
        var y0 = MathUtils.floor(start.y / GRID_SIZE)
        val x1 = MathUtils.floor(end.x / GRID_SIZE)
        val y1 = MathUtils.floor(end.y / GRID_SIZE)

        val dx = abs(x1 - x0)
        val dy = abs(y1 - y0)

        val stepX = if (x0 < x1) 1 else -1
        val stepY = if (y0 < y1) 1 else -1

        var error = dx - dy

        while (true) {
            val cellBodies = bodies[x0 to y0]

            if (cellBodies != null) {
                for (body in cellBodies) {
                    if (checkedBodies.contains(body)) continue
                    checkedBodies.add(body)

                    val radius = body.size / 2f
                    if (Intersector.intersectSegmentCircle(start, end, body.position, radius * radius)) {
                        result.add(body)
                    }
                }
            }

            if (x0 == x1 && y0 == y1) break
            val e2 = 2 * error
            if (e2 > -dy) {
                error -= dy
                x0 += stepX
            }
            if (e2 < dx) {
                error += dx
                y0 += stepY
            }
        }

        return result
    }

    //this was clankered
    fun resolveCollisions(deltaTime: Float) {
        val allBodies = bodies.values.flatten().distinct()

        for (bodyA in allBodies) {
            val radiusA = bodyA.size / 2f
            val neighbors = allCircle(bodyA.position, radiusA * 2f)
            for (bodyB in neighbors) {
                if (bodyA == bodyB) continue
                bodyA.collideWith(bodyB)
                bodyB.collideWith(bodyA)
                if (bodyA == bodyB || bodyA.hashCode() > bodyB.hashCode() || bodyA.overlapping || bodyB.overlapping) continue

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
