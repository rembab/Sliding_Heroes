package io.github.slidingHeroes // Make sure this matches your package!

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Application.ApplicationType
import com.badlogic.gdx.Game
import com.badlogic.gdx.math.Vector2

import io.github.slidingHeroes.mobile.scenes.ConnectionScene
import io.github.slidingHeroes.server.scenes.GameScene

class Main(val screenWidth : Int=0, val screenHeight: Int=0) : Game() {
    override fun create() {
        if (Gdx.app.type == ApplicationType.Android) {
            setScreen(ConnectionScene())
        } else {
            setScreen(GameScene(Vector2(screenWidth.toFloat(), screenHeight.toFloat())))
        }
    }
}
