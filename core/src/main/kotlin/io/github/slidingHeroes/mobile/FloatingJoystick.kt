package io.github.slidingHeroes.mobile

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.esotericsoftware.kryonet.Client
import io.github.slidingHeroes.util.JoyStickPressedMessage
import io.github.slidingHeroes.util.JoyStickReleasedMessage
import io.github.slidingHeroes.util.JoystickDraggedMessage

class FloatingJoystick(
    private val client: Client,
    private val radius: Float = 150f,
    private val knobRadius: Float = 50f
) : InputAdapter() {

    var isActive = false
        private set

    private val output = Vector2()
    private val center = Vector2()
    private val knob = Vector2()
    private val joystickDraggedMessage : JoystickDraggedMessage = JoystickDraggedMessage()
    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (pointer != 0) return false

        isActive = true

        val gameY = Gdx.graphics.height - screenY.toFloat()

        center.set(screenX.toFloat(), gameY)
        knob.set(center)
        output.setZero()
        client.sendTCP(JoyStickPressedMessage())
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        if (!isActive || pointer != 0) return false

        val gameY = Gdx.graphics.height - screenY.toFloat()
        val currentTouch = Vector2(screenX.toFloat(), gameY)

        val delta = currentTouch.cpy().sub(center)
        val distance = delta.len()

        if (distance <= radius) {
            knob.set(currentTouch)
            if (radius > 0) output.set(delta.scl(1f / radius))
        } else {
            delta.nor().scl(radius)
            knob.set(center).add(delta)

            output.set(delta.nor())
        }
        joystickDraggedMessage.x = output.x
        joystickDraggedMessage.y = output.y
        client.sendUDP(joystickDraggedMessage)
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (pointer != 0) return false

        isActive = false
        output.setZero()
        joystickDraggedMessage.x = 0f
        joystickDraggedMessage.y = 0f
        client.sendUDP(joystickDraggedMessage)
        client.sendTCP(JoyStickReleasedMessage())
        return true
    }

    fun draw(shape: ShapeRenderer) {
        if (!isActive) return

        shape.begin(ShapeRenderer.ShapeType.Filled)

        shape.color = Color(0.2f, 0.2f, 0.2f, 0.5f)
        shape.circle(center.x, center.y, radius)

        shape.color = Color.WHITE
        shape.circle(knob.x, knob.y, knobRadius)

        shape.end()
    }
}
