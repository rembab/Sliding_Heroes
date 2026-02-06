package io.github.slidingHeroes.util

import com.esotericsoftware.kryo.Kryo
import io.github.slidingHeroes.units.heroes.HeroRecord

class CharacterSelectedMessage(val heroIndex : Int = 0)

interface PlayerInput
class ButtonMessage(val pressed: Boolean = true) : PlayerInput
class GyroMessage(var x: Float = 0f, var y: Float = 0f) : PlayerInput

object Network {
    fun register(kryo: Kryo) {
        kryo.register(ButtonMessage::class.java)
        kryo.register(GyroMessage::class.java)
        kryo.register(HeroRecord::class.java)
        kryo.register(CharacterSelectedMessage::class.java)
    }
}
