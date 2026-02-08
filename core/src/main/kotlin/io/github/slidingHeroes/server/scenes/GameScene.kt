package io.github.slidingHeroes.server.scenes

import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.ScreenUtils
import com.esotericsoftware.kryonet.Server
import io.github.slidingHeroes.server.ConnectionObserver
import io.github.slidingHeroes.server.EnemiesController
import io.github.slidingHeroes.server.HeroesController
import io.github.slidingHeroes.server.LevelSpace
import io.github.slidingHeroes.server.ServerConnectionListener
import io.github.slidingHeroes.util.ButtonMessage
import io.github.slidingHeroes.util.CharacterSelectedMessage
import io.github.slidingHeroes.util.NetworkMessage
import io.github.slidingHeroes.util.Physics
import io.github.slidingHeroes.util.PlayerDisconnectedMessage
import io.github.slidingHeroes.util.PlayerInputMessage

class GameScene(val server: Server,
                screenSize : Vector2,
                players : HashSet<Pair<Int, Int>>) : ScreenAdapter(), ConnectionObserver {

    var levelSpace : LevelSpace = LevelSpace(Vector2.Zero, screenSize)
    val enemies = EnemiesController()
    val heroes = HeroesController()
    var spawnTimer = 1f
    val spawnCd = 1f

    init {
        ServerConnectionListener.addObserver(this)
        for ((id, cl) in players) {
            heroes.add(id, cl, levelSpace)
        }
    }

    override fun receiveMessage(id: Int, message: NetworkMessage) {
        if(message is PlayerInputMessage) {heroes.passInput(id, message)}
        if(message is PlayerDisconnectedMessage) {heroes.remove(id)}
        if(message is CharacterSelectedMessage) {heroes.add(id, message.heroIndex, levelSpace)}
    }

    override fun render(deltaTime: Float) {
        spawnTimer -= deltaTime
        if (spawnTimer <= 0f) {
            enemies.spawn(levelSpace, heroes)
            spawnTimer = spawnCd
        }
        ScreenUtils.clear(Color.DARK_GRAY)
        updateUnits(deltaTime)
        drawUnits()
        Physics.update(deltaTime)
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
