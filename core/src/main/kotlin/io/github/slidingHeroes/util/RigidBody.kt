package io.github.slidingHeroes.util

import com.badlogic.gdx.math.Vector2

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
    var overlapping: Boolean = false

    val tags: HashSet<BodyTag> = HashSet()

    init {
        Physics.addBody(this)
    }

    fun hasTag(tag: BodyTag) : Boolean = tags.contains(tag)

    fun hasAnyTag(argTags: Collection<BodyTag>) : Boolean = argTags.any { tags.contains(it) }
    fun addTag(tag: BodyTag) {tags.add(tag)}

    open fun collideWith(other: RigidBody) {}


}
