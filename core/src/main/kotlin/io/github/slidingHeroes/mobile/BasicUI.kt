package io.github.slidingHeroes.mobile

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad

/**
 * just a basic ui style
 */
object BasicSkin : Skin()
{
    init {
        val pixmap = Pixmap(1, 1, Pixmap.Format.RGBA8888)
        pixmap.setColor(Color.WHITE)
        pixmap.fill()
        this.add("white", Texture(pixmap))

        val generator = FreeTypeFontGenerator(Gdx.files.internal("arial.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()

        parameter.size = 48
        parameter.color = Color.WHITE
        parameter.borderWidth = 1f
        parameter.borderColor = Color.BLACK

        val sharpFont = generator.generateFont(parameter)
        generator.dispose()

        this.add("default", sharpFont)

        val textButtonStyle = TextButton.TextButtonStyle()
        textButtonStyle.up = this.newDrawable("white", Color.GRAY)
        textButtonStyle.down = this.newDrawable("white", Color.DARK_GRAY)
        textButtonStyle.font = this.getFont("default")
        this.add("default", textButtonStyle)

        val labelStyle = Label.LabelStyle()
        labelStyle.font = this.getFont("default")
        this.add("default", labelStyle)

        val textFieldStyle = TextField.TextFieldStyle()
        textFieldStyle.font = this.getFont("default")
        textFieldStyle.fontColor = Color.BLACK
        textFieldStyle.background = this.newDrawable("white", Color.LIGHT_GRAY)
        textFieldStyle.cursor = this.newDrawable("white", Color.RED)
        textFieldStyle.selection = this.newDrawable("white", Color.BLUE)
        this.add("default", textFieldStyle)
    }
}

/**
 * joystick skin for mobile
 */
object Joystick : Skin()
{
    init {

        val bgPixmap = Pixmap(200, 200, Pixmap.Format.RGBA8888)
        bgPixmap.setColor(Color.GRAY)
        bgPixmap.fillCircle(100, 100, 100)
        this.add("bg", Texture(bgPixmap))

        val knobPixmap = Pixmap(100, 100, Pixmap.Format.RGBA8888)
        knobPixmap.setColor(Color.LIGHT_GRAY)
        knobPixmap.fillCircle(50, 50, 50)
        this.add("knob", Texture(knobPixmap))

        val touchpadStyle = Touchpad.TouchpadStyle()
        touchpadStyle.background = this.getDrawable("bg")
        touchpadStyle.knob = this.getDrawable("knob")

        this.add("default", touchpadStyle)
    }
}

