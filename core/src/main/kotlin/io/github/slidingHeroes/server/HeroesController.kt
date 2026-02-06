package io.github.slidingHeroes.server

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import io.github.slidingHeroes.units.heroes.SelectableHeroes
import io.github.slidingHeroes.units.heroes.Hero
import io.github.slidingHeroes.util.PlayerInput

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
        for (hero in heroes.values) {hero.drawStatus(shape)}
    }

    fun update(deltaTime: Float) {
        for (hero in heroes.values) {hero.update(deltaTime)}
    }

    fun passInput(id: Int, inp : PlayerInput)
    {
        heroes[id]?.recieveInput(inp)
    }

    fun getHeroes() : MutableCollection<Hero> {return heroes.values}
}
