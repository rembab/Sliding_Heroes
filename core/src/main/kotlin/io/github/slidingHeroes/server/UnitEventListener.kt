package io.github.slidingHeroes.server

import io.github.slidingHeroes.units.heroes.Hero
import io.github.slidingHeroes.units.Unit
enum class UnitEvent {DIED, SPAWNED}

interface UnitEventObserver { fun receiveUnitEvent(unit: Unit, event : UnitEvent) }

object UnitEventListener {
    private val observers : ArrayList<UnitEventObserver> = arrayListOf()
    fun addObserver(observer: UnitEventObserver) {observers.add(observer)}
    fun removeObserver(observer: UnitEventObserver) {observers.remove(observer)}
    fun passEvent(unit: Unit, event: UnitEvent) {
        for (o in observers){ o.receiveUnitEvent(unit, event) }
    }

}
