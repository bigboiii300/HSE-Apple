package hseapp.app.utils.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//светлая палитра цветов которая просто нужна но не используется
private val LightColorPalette = lightColors(
    primary = Color(0xff0077ff),
    primaryVariant = Color(0xFFB3D6FF),
    onPrimary = Color.White,
    background = Color.White,
    onBackground = Color.Black,
)

//темная палитра цветов которая просто нужна но не используется
private val DarkColorPalette = darkColors(
    primary = Color(0xFFFFFFFF),
    primaryVariant = Color(0xFF292929),
    onPrimary = Color.White,
    background = Color(0xFF191919),
    onBackground = Color.White,
)

//просто размеры фигур которые тоже нужны
val HSEShapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(8.dp)
)

//декларация шрифта вашего супер классного дизайнера, на всякий оставлю вдруг понадобится когда-то,
//я манал это еще раз писать
/*private val gilroyFont = listOf(
    Font(R.font.gilroy_black, weight = FontWeight.Black),
    Font(R.font.gilroy_black_italic, weight = FontWeight.Black, style = FontStyle.Italic),
    Font(R.font.gilroy_bold, weight = FontWeight.Bold),
    Font(R.font.gilroy_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.gilroy_extra_bold, weight = FontWeight.ExtraBold),
    Font(R.font.gilroy_extra_bold_italic, weight = FontWeight.ExtraBold, style = FontStyle.Italic),
    Font(R.font.gilroy_light, weight = FontWeight.Light),
    Font(R.font.gilroy_light_italic, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(R.font.gilroy_medium, weight = FontWeight.Medium),
    Font(R.font.gilroy_medium_italic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(R.font.gilroy_regular, weight = FontWeight.Normal),
    Font(R.font.gilroy_regular_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.gilroy_semi_bold, weight = FontWeight.SemiBold),
    Font(R.font.gilroy_semi_bold_italic, weight = FontWeight.SemiBold, style = FontStyle.Italic),
    Font(R.font.gilroy_thin, weight = FontWeight.Thin),
    Font(R.font.gilroy_thin_italic, weight = FontWeight.Thin, style = FontStyle.Italic),
    Font(R.font.gilroy_ultra_light, weight = FontWeight.ExtraLight),
    Font(R.font.gilroy_ultra_light_italic, weight = FontWeight.ExtraLight, style = FontStyle.Italic),
)*/

/**
 * стили текстов
 */
val HSETypography = Typography(
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h5 = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold
    ),
    h6 = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold
    )
)

//просто тема, ничего не значит практически
@Composable
fun HSETheme(isDark: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    //определяем цветовую схему приложения
    val colors = if (isDark) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = HSETypography,
        shapes = HSEShapes
    ) {
        CompositionLocalProvider(
            //провайдим наши цвета приложения
            LocalHSEColors provides if (isDark) HSEColors.darkPalette else HSEColors.lightPalette,
            content = content
        )
    }
}