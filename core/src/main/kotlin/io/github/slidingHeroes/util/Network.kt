package io.github.slidingHeroes.util

import com.esotericsoftware.kryo.Kryo

// anything passed through the network between server and player android controller
interface NetworkMessage

class PlayerConnectedMessage : NetworkMessage

class PlayerDisconnectedMessage : NetworkMessage

// heroIndex refers to the global SelectableHeroes object
class CharacterSelectedMessage(val heroIndex : Int = 0) : NetworkMessage

// Any input the player gives to control their character
interface PlayerInputMessage : NetworkMessage
class ButtonMessage(val pressed: Boolean = true, var id : Int = 0) : PlayerInputMessage
class GyroMessage(var x: Float = 0f, var y: Float = 0f) : PlayerInputMessage

class JoystickMessage(var pressed : Boolean = true, var id : Int = 0) : PlayerInputMessage
class JoystickDraggedMessage(var x: Float = 0f, var y: Float = 0f, var id : Int = 0) : PlayerInputMessage

// registering all of the messages as network objects to be sent between devices
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
