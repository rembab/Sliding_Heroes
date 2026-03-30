package io.github.slidingHeroes.server

import io.github.slidingHeroes.world.units.Unit

interface Updatable{
    fun startUpdate()
    {
        UpdateBus.add(this)
    }
    fun update(deltaTime: Float)
    fun stopUpdate()
    {
       UpdateBus.remove(this)
    }
}

object UpdateBus {
    private val observers = HashSet<Updatable>()
    private val toRemove = HashSet<Updatable>()
    private val toAdd = HashSet<Updatable>()

    fun add(updatable: Updatable) {toAdd.add(updatable)}
    fun remove(updatable: Updatable) {toRemove.add(updatable)}

    fun update(deltaTime: Float) {
        toAdd.forEach { observers.add(it) }
        toAdd.clear()
        toRemove.forEach { observers.remove(it) }
        toRemove.clear()
        observers.forEach {
            if (it is Unit)
                println(it.status)
            it.update(deltaTime)
        }
    }
}
