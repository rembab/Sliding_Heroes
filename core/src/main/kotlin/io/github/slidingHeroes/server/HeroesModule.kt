package io.github.slidingHeroes.server

import com.badlogic.gdx.math.Vector2
import io.github.slidingHeroes.units.Unit
import io.github.slidingHeroes.units.heroes.SelectableHeroes
import io.github.slidingHeroes.units.heroes.Hero
import io.github.slidingHeroes.util.PlayerInputMessage

class HeroesModule {
    private val heroes = HashMap<Int, Hero>()

    fun add(playerID: Int, heroID: Int, levelSpace: LevelSpace) {
        heroes[playerID] = SelectableHeroes[heroID].prefab.constructors.first().call(levelSpace, this, playerID)
        UnitEventListener.passEvent(heroes[playerID]!!, UnitEvent.SPAWNED)
        heroes[playerID]!!.position = levelSpace.middle
    }

    fun disconnected(id : Int)
    {
        if (heroes[id] != null) { // if the player has died prior, they will already be removed
            UnitEventListener.passEvent(heroes[id]!!, UnitEvent.DIED)
            heroes[id]!!.die()
            remove(id)
        }
    }
    fun remove(id: Int) {
        heroes.remove(id)
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
