package io.github.slidingHeroes.util

import com.esotericsoftware.kryo.Kryo

interface NetworkMessage

class PlayerConnectedMessage : NetworkMessage

class PlayerDisconnectedMessage : NetworkMessage

class CharacterSelectedMessage(val heroIndex : Int = 0) : NetworkMessage

interface PlayerInputMessage : NetworkMessage
class ButtonMessage(val pressed: Boolean = true, var id : Int = 0) : PlayerInputMessage
class GyroMessage(var x: Float = 0f, var y: Float = 0f) : PlayerInputMessage

class JoystickMessage(var pressed : Boolean = true, var id : Int = 0) : PlayerInputMessage
class JoystickDraggedMessage(var x: Float = 0f, var y: Float = 0f, var id : Int = 0) : PlayerInputMessage
object Network {
    fun register(kryo: Kryo) {
        kryo.register(PlayerConnectedMessage::class.java)
        kryo.register(PlayerDisconnectedMessage::class.java)
        kryo.register(ButtonMessage::class.java)
        kryo.register(GyroMessage::class.java)
        kryo.register(JoystickDraggedMessage::class.java)
        kryo.register(JoystickMessage::class.java)
        kryo.register(CharacterSelectedMessage::class.java)
    }
}
