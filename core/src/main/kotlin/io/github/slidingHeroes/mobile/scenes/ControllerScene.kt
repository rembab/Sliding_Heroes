package io.github.slidingHeroes.mobile.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.esotericsoftware.kryonet.Client
import io.github.slidingHeroes.mobile.FloatingJoystick
import io.github.slidingHeroes.mobile.Joystick
import io.github.slidingHeroes.util.ButtonMessage
import io.github.slidingHeroes.util.GyroMessage

class ControllerScene (val client : Client) : ScreenAdapter() {
    private val shape : ShapeRenderer = ShapeRenderer()

    private val joystick = FloatingJoystick(client, radius = 200f)
    private val gyro: GyroMessage = GyroMessage(0f, 0f)

    override fun render(deltaTime: Float) {
        ScreenUtils.clear(Color.DARK_GRAY)
        joystick.draw(shape)

        if (client.isConnected) {
            if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer)) {
                gyro.x = Gdx.input.accelerometerY
                gyro.y = -Gdx.input.accelerometerX
                client.sendUDP(gyro)
            }
        }
    }

    override fun show() {
        Gdx.input.inputProcessor = joystick
    }

    override fun dispose() {
        shape.dispose()
        client.stop()
    }
}
