package io.github.slidingHeroes.mobile.scenes

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.esotericsoftware.kryonet.Client
import io.github.slidingHeroes.mobile.BasicSkin
import io.github.slidingHeroes.mobile.scenes.ControllerScene
import io.github.slidingHeroes.util.Network
import kotlin.concurrent.thread

class ConnectionScene : ScreenAdapter() {
    private val stage = Stage(ScreenViewport())
    private val skin = BasicSkin
    private val client = Client()

    init {
        Network.register(client.kryo)
        client.start()

        val table = Table()
        table.setFillParent(true)
        stage.addActor(table)

        val ipLabel = Label("Enter PC IP Address:", skin)
        ipLabel.fontScaleX*=2
        ipLabel.fontScaleY*=2
        val ipField = TextField("10.42.0.1", skin)
        val connectBtn = TextButton("Connect to PC", skin)
        val statusLabel = Label("Ready", skin)
        statusLabel.fontScaleX*=2
        statusLabel.fontScaleY*=2

        table.add(ipLabel).pad(10f).row()
        table.add(ipField).width(1000f).height(200f).pad(10f).row()
        table.add(connectBtn).width(800f).height(240f).pad(10f).row()
        table.add(statusLabel).pad(40f)

        connectBtn.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                statusLabel.setText("Connecting...")
                val ip = ipField.text

                thread {
                    try {
                        client.connect(5000, ip, 54555, 54777)
                        Gdx.app.postRunnable {
                            (Gdx.app.applicationListener as Game).setScreen(CharacterSelectionScene(client))
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Gdx.app.postRunnable {
                            statusLabel.setText("Failed: ${e.message}")
                        }
                    }
                }
            }
        })
    }

    override fun show() {
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(Color.DARK_GRAY)
        stage.act(delta)
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun dispose() {
        stage.dispose()
        skin.dispose()
    }

}
