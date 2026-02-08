package io.github.slidingHeroes.entities

import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.server.LevelSpace
import io.github.slidingHeroes.util.RigidBody
import kotlin.reflect.KClass

class Projectile(val position : Vector2,
                 val direction: Vector2,
                 val levelSpace: LevelSpace,
                 val hittable : ArrayList<KClass<RigidBody>>,
                 val speed : Float = 10f) {


}
