package io.github.slidingHeroes.mobile.controls

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.esotericsoftware.kryonet.Client
import io.github.slidingHeroes.util.JoystickDraggedMessage
import io.github.slidingHeroes.util.JoystickMessage

/**
 * a floating joystick controller
 */
class FloatingJoystick(
    client: Client,
    inputId: Int = 0,
    minX: Float? = null,
    maxX: Float? = null,
) : MobileControl(client, inputId, minX, maxX) {

    private val radius: Float = 150f
    private val knobRadius: Float = 50f
    var isActive = false
        private set

    private var activePointer = -1

    private val output = Vector2()
    private val center = Vector2()
    private val knob = Vector2()

    private val joystickDraggedMessage = JoystickDraggedMessage(id = inputId)

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (isActive) return false

        val gameY = Gdx.graphics.height - screenY.toFloat()

        if (minX != null && screenX < minX) return false
        if (maxX != null && screenX > maxX) return false

        activePointer = pointer
        isActive = true

        center.set(screenX.toFloat(), gameY)
        knob.set(center)
        output.setZero()

        client.sendTCP(JoystickMessage(true,inputId))
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        if (!isActive || pointer != activePointer) return false

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
        if (pointer != activePointer) return false


        if (minX != null && screenX < minX) return false
        if (maxX != null && screenX > maxX) return false

        isActive = false
        activePointer = -1
        output.setZero()

        client.sendTCP(JoystickMessage(false, inputId))
        return true
    }

    override fun draw(shape: ShapeRenderer) {
        if (!isActive) return

        shape.color = Color(0.2f, 0.2f, 0.2f, 0.5f)
        shape.circle(center.x, center.y, radius)
        shape.color = Color.WHITE
        shape.circle(knob.x, knob.y, knobRadius)
    }
}
