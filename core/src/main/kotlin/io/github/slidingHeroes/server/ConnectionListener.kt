package io.github.slidingHeroes.server

import com.esotericsoftware.kryonet.Connection
import com.esotericsoftware.kryonet.Listener
import io.github.slidingHeroes.characters.heroes.HeroRecord
import io.github.slidingHeroes.util.CharacterSelectedMessage
import io.github.slidingHeroes.util.PlayerInput


class ConnectionListener (val gameApp : GameApp) : Listener {
    override fun connected(connection: Connection?) {
        if (connection == null) {return}
        println("Player connected! ID: ${connection.id}")
    }
    override fun received(connection: Connection?, obj: Any?) {
        if (connection == null) {return}
        if (obj is PlayerInput) gameApp.heroes.passInput(connection.id, obj)
        if (obj is CharacterSelectedMessage)
            gameApp.heroes.add(connection.id, obj.heroIndex, gameApp.scene)
    }
    override fun disconnected(connection: Connection?) {
        if (connection == null) {return}
        println("Player disconnected! ID: ${connection.id}")
        gameApp.heroes.remove(connection.id)
    }
}

