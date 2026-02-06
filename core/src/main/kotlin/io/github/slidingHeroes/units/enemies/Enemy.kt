package io.github.slidingHeroes.units.enemies

import io.github.slidingHeroes.server.HeroesController
import io.github.slidingHeroes.server.LevelSpace

abstract class Enemy(levelSpace: LevelSpace, heroes: HeroesController) {
    abstract fun draw()
}
