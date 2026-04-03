package io.github.slidingHeroes.server.scenes

import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.ScreenUtils
import com.esotericsoftware.kryonet.Server
import io.github.slidingHeroes.server.ConnectionObserver
import io.github.slidingHeroes.server.EnemiesModule
import io.github.slidingHeroes.server.rendering.DrawingBus
import io.github.slidingHeroes.server.HeroesModule
import io.github.slidingHeroes.server.LevelSpace
import io.github.slidingHeroes.server.ServerConnectionListener
import io.github.slidingHeroes.server.UnitsModule
import io.github.slidingHeroes.server.UpdateBus
import io.github.slidingHeroes.server.rendering.Renderer
import io.github.slidingHeroes.util.CharacterSelectedMessage
import io.github.slidingHeroes.util.NetworkMessage
import io.github.slidingHeroes.util.PlayerDisconnectedMessage
import io.github.slidingHeroes.util.PlayerInputMessage

/**
 * main game scene where enemies and players fight
 */
class GameScene(val server: Server,
                screenSize : Vector2,
                players : HashSet<Pair<Int, Int>>) : ScreenAdapter(), ConnectionObserver {

    var levelSpace : LevelSpace = LevelSpace(Vector2.Zero, screenSize)
    val units = UnitsModule()
    val heroes = HeroesModule()
    val enemies = EnemiesModule(levelSpace, heroes)

    init {
        ServerConnectionListener.addObserver(this)
        for ((id, cl) in players) {
            heroes.add(id, cl, levelSpace)
        }
    }

    override fun receiveMessage(id: Int, message: NetworkMessage) {
        if(message is PlayerInputMessage) {heroes.passInput(id, message)}
        if(message is CharacterSelectedMessage) {heroes.add(id, message.heroIndex, levelSpace)}
        if(message is PlayerDisconnectedMessage) {heroes.disconnected(id)}
    }

    override fun render(deltaTime: Float) {
        ScreenUtils.clear(Color.DARK_GRAY)
        UpdateBus.update(deltaTime)
        drawUnits()
    }

    fun drawUnits()
    {
        val rend = Renderer()
        rend.begin()
        DrawingBus.draw(rend)
        rend.end()
    }
    override fun dispose() {
        server.stop()
        ServerConnectionListener.removeObserver(this)
    }
}
