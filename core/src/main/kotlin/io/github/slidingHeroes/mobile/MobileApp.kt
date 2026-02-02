package io.github.slidingHeroes.mobile

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Peripheral
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.esotericsoftware.kryonet.Client
import io.github.slidingHeroes.util.ButtonMessage
import io.github.slidingHeroes.util.GyroMessage
import io.github.slidingHeroes.util.Network
import kotlin.concurrent.thread


class MobileApp : ScreenAdapter() {
    private val client = Client()
    private val gyro: GyroMessage = GyroMessage(0f, 0f)

    init {
        Network.register(client.kryo)
        client.start()

        thread {
            try {
                client.connect(5000, "10.42.0.1", 54555, 54777)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(Color.DARK_GRAY)

        if (client.isConnected) {
            if (Gdx.input.justTouched()) {
                client.sendTCP(ButtonMessage(true))
            }
            if (Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)) {
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

