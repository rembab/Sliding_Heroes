package io.github.slidingHeroes // Make sure this matches your package!

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Application.ApplicationType
import com.badlogic.gdx.Game
import com.badlogic.gdx.math.Vector2

import io.github.slidingHeroes.mobile.MobileApp
import io.github.slidingHeroes.server.GameApp

class Main(val screenWidth : Int, val screenHeight : Int) : Game() {
    override fun create() {
        if (Gdx.app.type == ApplicationType.Android) {
            // I am a Phone -> Be a Controller
            setScreen(MobileApp())
        } else {
            // I am a PC -> Be the Game Console
            setScreen(GameApp(Vector2(screenWidth.toFloat(), screenHeight.toFloat())))
        }
    }
}
