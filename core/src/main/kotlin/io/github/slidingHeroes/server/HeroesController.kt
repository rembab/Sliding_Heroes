package io.github.slidingHeroes.server

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import io.github.slidingHeroes.server.world.Hero
import io.github.slidingHeroes.server.world.LevelScene
import io.github.slidingHeroes.util.PlayerInput

class HeroesController {
    private val heroes = HashMap<Int, Hero>()

    fun add(id: Int, scene: LevelScene) {
        heroes[id] = Hero(scene)
        heroes[id]?.position = scene.middle
    }

    fun remove(id: Int) {
        heroes.remove(id)
    }

    fun draw(shape: ShapeRenderer) {
        for (hero in heroes.values) {hero.draw(shape)}
    }

    fun update(deltaTime: Float) {
        for (hero in heroes.values) {hero.update(deltaTime)}
    }

    fun passInput(id: Int, inp : PlayerInput)
    {
        heroes[id]?.recieveInput(inp)
    }
}
