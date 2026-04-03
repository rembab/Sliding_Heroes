package io.github.slidingHeroes.mobile.controls

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.esotericsoftware.kryonet.Client
import io.github.slidingHeroes.util.ButtonMessage
import io.github.slidingHeroes.util.JoystickDraggedMessage
import io.github.slidingHeroes.util.JoystickMessage

/**
 * a button control taking up half of the screen. Sends a ButtonMessage when pressed and released
 */
class BigButton(
    client: Client,
    inputId: Int = 0,
    minX: Float? = null,
    maxX: Float? = null,
) : MobileControl(client, inputId, minX, maxX) {

    var isActive = false
        private set

    private var activePointer = -1

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (isActive) return false

        if (minX != null && screenX < minX) return false
        if (maxX != null && screenX > maxX) return false

        activePointer = pointer
        isActive = true

        client.sendTCP(ButtonMessage(true, inputId))

        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (pointer != activePointer) return false

        if (minX != null && screenX < minX) return false
        if (maxX != null && screenX > maxX) return false


        isActive = false
        activePointer = -1

        client.sendTCP(ButtonMessage(false, inputId))
        return true
    }

    override fun draw(shape: ShapeRenderer) {
        if (!isActive) return

    }
}
