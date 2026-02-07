package io.github.slidingHeroes.server

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.units.heroes.SelectableHeroes
import io.github.slidingHeroes.units.heroes.Hero
import io.github.slidingHeroes.util.PlayerInputMessage

class HeroesController {
    private val heroes = HashMap<Int, Hero>()

    fun add(playerID: Int, heroID: Int, levelSpace: LevelSpace) {
        heroes[playerID] = SelectableHeroes[heroID].prefab.constructors.first().call(levelSpace)
        heroes[playerID]?.position = levelSpace.middle
    }

    fun remove(id: Int) {
        heroes.remove(id)
    }

    fun draw(shape: ShapeRenderer) {
        for (hero in heroes.values) {hero.draw(shape)}
    }

    fun drawStatusBars(shape: ShapeRenderer) {

        for (hero in heroes.values) {hero.drawStatus(shape)}
    }
    fun update(deltaTime: Float) {
        for (hero in heroes.values) {hero.update(deltaTime)}
    }

    fun passInput(id: Int, inp : PlayerInputMessage)
    {
        heroes[id]?.receiveInput(inp)
    }

    fun closestTo(position : Vector2) : Hero?
    {
        return heroes.values.minByOrNull { it.position.dst2(position) }
    }
}
