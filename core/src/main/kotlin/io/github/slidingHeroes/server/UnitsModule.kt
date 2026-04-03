package io.github.slidingHeroes.server

import io.github.slidingHeroes.world.units.Unit

/**
 * main game module responsible for handling units - spawning, updating them and removing dead ones
 * also resolving any effects that happen when the above occur
 */
class UnitsModule : UnitEventObserver, Updatable {
    init{
        UnitEventListener.addObserver(this)
        startUpdate()
    }
    private val units : ArrayList<Unit> = ArrayList()

    private val deadUnits : ArrayList<Unit> = ArrayList()

    fun add(unit: Unit) {units.add(unit)}

    override fun update(deltaTime: Float) {
        for (u in deadUnits) {
            units.remove(u)
        }
        deadUnits.clear()
        for (u in units) u.update(deltaTime)
    }


    override fun receiveUnitEvent(unit: Unit, event: UnitEvent) {
        when(event) {
            UnitEvent.DIED -> deadUnits.add(unit)
            UnitEvent.SPAWNED -> add(unit)
        }
    }
}
