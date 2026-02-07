package io.github.slidingHeroes.server.scenes

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.esotericsoftware.kryonet.Client
import com.esotericsoftware.kryonet.Server
import com.sun.jdi.connect.spi.Connection
import io.github.slidingHeroes.mobile.BasicSkin
import io.github.slidingHeroes.mobile.scenes.CharacterSelectionScene
import io.github.slidingHeroes.mobile.scenes.ControllerScene
import io.github.slidingHeroes.server.ConnectionObserver
import io.github.slidingHeroes.server.HeroesController
import io.github.slidingHeroes.server.ServerConnectionListener
import io.github.slidingHeroes.units.heroes.SelectableHeroes
import io.github.slidingHeroes.util.CharacterSelectedMessage
import io.github.slidingHeroes.util.Network
import io.github.slidingHeroes.util.NetworkMessage
import io.github.slidingHeroes.util.PlayerConnectedMessage
import io.github.slidingHeroes.util.PlayerDisconnectedMessage
import kotlin.concurrent.thread

class MenuScene(screenSize : Vector2) : ScreenAdapter(), ConnectionObserver {
    private val server: Server = Server()
    private val stage = Stage(ScreenViewport())
    private val skin = BasicSkin

    private var activePlayersLabel: Label = Label("", skin)



    private var playerClassChoices : HashSet<Pair<Int, Int>> = HashSet()
    private var numPlayers = 0

    private fun updatePlayerCount(delta: Int){
        numPlayers += delta
        activePlayersLabel.setText("Connected players: $numPlayers")
    }

    init {
        Network.register(server.kryo)
        server.start()
        server.bind(54555, 54777)
        server.addListener(ServerConnectionListener)
        ServerConnectionListener.addObserver(this)


        val table = Table()
        table.setFillParent(true)
        stage.addActor(table)
        table.add(activePlayersLabel).pad(10f).row()
        updatePlayerCount(0)

        val startButton = TextButton("Start", skin)
        table.add(startButton).pad(10f).row()
        startButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                thread {
                    try {
                        Gdx.app.postRunnable {
                            (Gdx.app.applicationListener as Game).setScreen(
                                GameScene(server, screenSize, playerClassChoices))
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        })

    }

    override fun receiveMessage(id: Int, message: NetworkMessage) {
        if (message is PlayerConnectedMessage) updatePlayerCount(1)
        if (message is PlayerDisconnectedMessage) updatePlayerCount(-1)
        if (message is CharacterSelectedMessage) {playerClassChoices.add(Pair(id, message.heroIndex))}
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(Color.DARK_GRAY)
        stage.act(delta)
        stage.draw()
    }

    override fun show() {
        Gdx.input.inputProcessor = stage
    }


    override fun dispose() {
        ServerConnectionListener.removeObserver(this)
        stage.dispose()
        skin.dispose()
    }
}
