package io.github.slidingHeroes.mobile.scenes

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.esotericsoftware.kryonet.Client
import io.github.slidingHeroes.characters.heroes.SelectableHeroes
import io.github.slidingHeroes.mobile.BasicSkin
import io.github.slidingHeroes.util.CharacterSelectedMessage
import io.github.slidingHeroes.util.Network
import kotlin.concurrent.thread

class CharacterSelectionScene(val client : Client) : ScreenAdapter() {
    private val stage = Stage(ScreenViewport())
    private val skin = BasicSkin


    init {
        Network.register(client.kryo)
        client.start()

        val table = Table()
        table.setFillParent(true)
        stage.addActor(table)

        val promptLabel = Label("Select your class!", skin)

        table.add(promptLabel).pad(10f).row()

        for (i in SelectableHeroes.indices){
            val selectButton = TextButton(SelectableHeroes[i].name, skin)
            val descriptionLabel = Label(SelectableHeroes[i].description, skin)
            table.add(selectButton).pad(10f)
            table.add(descriptionLabel).pad(10f).row()
            selectButton.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    thread {
                        try {
                            client.sendTCP(CharacterSelectedMessage(i))
                            Gdx.app.postRunnable {
                                (Gdx.app.applicationListener as Game).setScreen(ControllerScene(client))
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            })
        }

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
