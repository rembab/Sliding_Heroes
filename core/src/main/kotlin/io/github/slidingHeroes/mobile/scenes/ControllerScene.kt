package io.github.slidingHeroes.mobile.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.esotericsoftware.kryonet.Client
import io.github.slidingHeroes.util.ButtonMessage
import io.github.slidingHeroes.util.GyroMessage

class ControllerScene (val client : Client) : ScreenAdapter() {
    private val gyro: GyroMessage = GyroMessage(0f, 0f)

    override fun render(delta: Float) {
        ScreenUtils.clear(Color.DARK_GRAY)

        if (client.isConnected) {
            if (Gdx.input.justTouched()) {
                client.sendTCP(ButtonMessage(true))
            }
            if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer)) {
                gyro.x = Gdx.input.accelerometerY
                gyro.y = -Gdx.input.accelerometerX
                client.sendUDP(gyro)
            }
        }
    }

    override fun dispose() {
        client.stop()
    }
}
