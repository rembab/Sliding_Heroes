package io.github.slidingHeroes.server
import io.github.slidingHeroes.world.units.Unit

/**
 * events that happen to units
 * this is done so that implementing abilities such as:
 * "get X when an enemy dies"
 * is easy - we just make that ability subscribe to a correct listener
 */
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
