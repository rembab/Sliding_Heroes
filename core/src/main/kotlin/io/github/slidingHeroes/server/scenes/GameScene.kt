package io.github.slidingHeroes.server.scenes

import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.ScreenUtils
import com.esotericsoftware.kryonet.Server
import io.github.slidingHeroes.server.ConnectionListener
import io.github.slidingHeroes.server.HeroesController
import io.github.slidingHeroes.server.LevelSpace
import io.github.slidingHeroes.util.Network

class GameScene(val screenSize : Vector2) : ScreenAdapter() {
    private val server = Server()

    var scene : LevelSpace = LevelSpace(Vector2.Zero, screenSize)
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
