package hseapp.app.utils.compose

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Цветовая гамма приложения
 */
data class HSEColors(
    val primary: Color, //главный цвет приложения
    val onPrimary: Color, //цвет текста на главном тексте приложения
    val background: Color, //цвет фона
    val textLabel: Color, //цвет текста на фоне
    val textBody: Color, //цвет текста на фоне но полупрозрачный
    val textFieldBackground: Color, //фон поля ввода (и еще чего-то)
    val textFieldStroke: Color, //обводка поля ввода (и другие обводки и разделители)
    val negative: Color //злой и негативный блин цвет, не добряк короче (красный)
) {
    
    companion object {
        
        val darkPalette = HSEColors(
            primary = Color(0xFF74B4FF),
            onPrimary = Color(0xFF191919),
            background = Color(0xFF191919),
            textLabel = Color.White,
            textBody = Color(0xFF909499),
            textFieldBackground = Color(0xFF232324),
            textFieldStroke = Color(0xFF3e3e3f),
            negative = Color(0xFFFF5C5C)
        )
        val lightPalette = HSEColors(
            primary = Color(0xff0077ff),
            onPrimary = Color.White,
            background = Color.White,
            textLabel = Color.Black,
            textBody = Color(0xFF6d7984),
            textFieldBackground = Color(0xFFf2f3f5),
            textFieldStroke = Color(0xFFd5d5d7),
            negative = Color(0xFFFF2E2E)
        )
    }
}

//компоузовская штука позволяющая цвет этот получать в любом месте, реализация в другом месте
val LocalHSEColors = compositionLocalOf<HSEColors> { error("LocalHSEColors provides nothing") }
