package io.github.slidingHeroes.server.scenes

import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.ScreenUtils
import com.esotericsoftware.kryonet.Server
import io.github.slidingHeroes.server.ConnectionListener
import io.github.slidingHeroes.server.EnemiesController
import io.github.slidingHeroes.server.HeroesController
import io.github.slidingHeroes.server.LevelSpace
import io.github.slidingHeroes.util.Network

class GameScene(screenSize : Vector2) : ScreenAdapter() {
    private val server = Server()

    var levelSpace : LevelSpace = LevelSpace(Vector2.Zero, screenSize)
    val heroes = HeroesController()
    val enemies = EnemiesController()

    init {
        Network.register(server.kryo)
        server.start()
        server.bind(54555, 54777)
        server.addListener(ConnectionListener(this))

        enemies.spawn(levelSpace, heroes)
    }


    override fun render(deltaTime: Float) {
        ScreenUtils.clear(Color.DARK_GRAY)

        updateUnits(deltaTime)
        drawUnits()
    }

    fun updateUnits(deltaTime: Float)
    {
        heroes.update(deltaTime)
        enemies.update(deltaTime)
        enemies.resetTargets()
    }

    fun drawUnits()
    {
        val shape = ShapeRenderer()
        shape.begin(ShapeRenderer.ShapeType.Filled)
        heroes.draw(shape)
        enemies.draw(shape)
        heroes.drawStatusBars(shape)
        enemies.drawStatusBars(shape)
        shape.end()
    }
    override fun dispose() {
        server.stop()
    }
}
