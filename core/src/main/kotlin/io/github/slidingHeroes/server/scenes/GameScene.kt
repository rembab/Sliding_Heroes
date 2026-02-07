package io.github.slidingHeroes.server.scenes

import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.ScreenUtils
import com.esotericsoftware.kryonet.Listener
import com.esotericsoftware.kryonet.Server
import io.github.slidingHeroes.server.ConnectionObserver
import io.github.slidingHeroes.server.EnemiesController
import io.github.slidingHeroes.server.HeroesController
import io.github.slidingHeroes.server.LevelSpace
import io.github.slidingHeroes.server.ServerConnectionListener
import io.github.slidingHeroes.util.NetworkMessage
import io.github.slidingHeroes.util.PlayerInputMessage

class GameScene(val server: Server,
                screenSize : Vector2,
                players : HashSet<Pair<Int, Int>>) : ScreenAdapter(), ConnectionObserver {

    var levelSpace : LevelSpace = LevelSpace(Vector2.Zero, screenSize)
    val enemies = EnemiesController()
    val heroes = HeroesController()

    init {
        ServerConnectionListener.addObserver(this)
        for ((id, cl) in players) {
            heroes.add(id, cl, levelSpace)
        }
        enemies.spawn(levelSpace, heroes)
    }

    override fun receiveMessage(id: Int, message: NetworkMessage) {
        if(message is PlayerInputMessage) {heroes.passInput(id, message)}
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
