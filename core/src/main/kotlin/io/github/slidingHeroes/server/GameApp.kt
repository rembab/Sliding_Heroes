package io.github.slidingHeroes.server

import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.ScreenUtils
import com.esotericsoftware.kryonet.Server
import io.github.slidingHeroes.server.world.LevelScene
import io.github.slidingHeroes.util.Network
import io.github.slidingHeroes.util.PlayerInput

class GameApp(val screenSize : Vector2) : ScreenAdapter() {
    private val server = Server()

    var scene : LevelScene = LevelScene(Vector2.Zero,screenSize)
    val heroes = HeroesController()

    init {
        Network.register(server.kryo)
        server.start()
        server.bind(54555, 54777)
        server.addListener(ConnectionListener(this))
    }


    override fun render(delta: Float) {
        ScreenUtils.clear(Color.DARK_GRAY)
        val shape = ShapeRenderer()
        heroes.update(delta)
        shape.begin(ShapeRenderer.ShapeType.Filled)
        heroes.draw(shape)
        shape.end()

    }

    override fun dispose() {
        server.stop()
    }
}

