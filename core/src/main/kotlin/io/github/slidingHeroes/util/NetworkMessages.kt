package io.github.slidingHeroes.util

import com.esotericsoftware.kryo.Kryo

interface PlayerInput
class ButtonMessage(val pressed: Boolean = true) : PlayerInput
class GyroMessage(var x: Float = 0f, var y: Float = 0f) : PlayerInput

object Network {
    fun register(kryo: Kryo) {
        kryo.register(ButtonMessage::class.java)
        kryo.register(GyroMessage::class.java)
    }
}
