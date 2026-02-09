package io.github.slidingHeroes.mobile.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.esotericsoftware.kryonet.Client
import io.github.slidingHeroes.mobile.controls.FloatingJoystick
import io.github.slidingHeroes.mobile.Joystick
import io.github.slidingHeroes.mobile.controls.MobileControl
import io.github.slidingHeroes.units.heroes.SelectableHeroes
import io.github.slidingHeroes.util.ButtonMessage
import io.github.slidingHeroes.util.GyroMessage

class ControllerScene (val client : Client, val heroIndex: Int) : ScreenAdapter() {
    private val controlLeft : MobileControl =
        SelectableHeroes[heroIndex].controlLeft.constructors.first().call(client, 0,
            0f,
            Gdx.graphics.width/2f)
    private val controlRight : MobileControl =
        SelectableHeroes[heroIndex].controlRight.constructors.first().call(client, 1,
            Gdx.graphics.width/2f,
            Gdx.graphics.width.toFloat() )

    private val shape : ShapeRenderer = ShapeRenderer()
    private val gyro: GyroMessage = GyroMessage(0f, 0f)

    override fun render(deltaTime: Float) {

        ScreenUtils.clear(Color.DARK_GRAY)
        shape.begin(ShapeRenderer.ShapeType.Filled)
        controlLeft.draw(shape)
        controlRight.draw(shape)
        shape.end()

        if (client.isConnected) {
            if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer)) {
                gyro.x = Gdx.input.accelerometerY
                gyro.y = -Gdx.input.accelerometerX
                client.sendUDP(gyro)
            }
        }
    }

    override fun show() {
        val inputs = InputMultiplexer()
        inputs.addProcessor(controlRight)
        inputs.addProcessor(controlLeft)

        Gdx.input.inputProcessor = inputs
    }

    override fun dispose() {
        shape.dispose()
        client.stop()
    }
}
