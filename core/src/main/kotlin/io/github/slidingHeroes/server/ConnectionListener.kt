package io.github.slidingHeroes.server

import com.esotericsoftware.kryonet.Connection
import com.esotericsoftware.kryonet.Listener
import io.github.slidingHeroes.util.NetworkMessage
import io.github.slidingHeroes.util.PlayerConnectedMessage
import io.github.slidingHeroes.util.PlayerDisconnectedMessage


interface ConnectionObserver
{
    fun receiveMessage(id : Int, message: NetworkMessage)
}

object ServerConnectionListener : Listener {
    private val observers: MutableSet<ConnectionObserver> = hashSetOf()
    fun addObserver(connectionObserver: ConnectionObserver) {observers.add(connectionObserver)}
    fun removeObserver(connectionObserver: ConnectionObserver) {observers.remove(connectionObserver)}

    fun notifyObservers(id : Int, message: NetworkMessage)
    {
        for (observer in observers) observer.receiveMessage(id, message)
    }

    override fun connected(connection: Connection?) {
        if (connection == null) {return}
        println("Player connected! ID: ${connection.id}")
        notifyObservers(connection.id, PlayerConnectedMessage())

    }
    override fun received(connection: Connection?, obj: Any?) {
        if (connection == null) {return}
        try {
            if (obj is NetworkMessage) notifyObservers(connection.id, obj)
            else throw IllegalArgumentException("Wrong network message object!!!")
        }
        catch (e: Exception) {println(e.message)}

    }
    override fun disconnected(connection: Connection?) {
        if (connection == null) {return}
        println("Player disconnected! ID: ${connection.id}")
        notifyObservers(connection.id, PlayerDisconnectedMessage())
    }
}

