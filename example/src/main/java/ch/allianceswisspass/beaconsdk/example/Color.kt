package ch.allianceswisspass.beaconsdk.example

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object AspColor {
    val Red = Color(color = 0xFFC51416)
    val DarkRed = Color(color = 0xFFAF1602)
    val Anthracite = Color(color = 0xFF333333)
    val Gray1 = Color(color = 0xFFEEEEEE)
    val Gray2 = Color(color = 0xFFDDDDDD)
    val Gray3 = Color(color = 0xFF767676)
}

object AspColorScheme {

    val Dark = darkColorScheme(
        primary = AspColor.Red,
        onPrimary = Color.White,
        secondary = AspColor.DarkRed,
        onSecondary = Color.White,
        tertiary = AspColor.DarkRed,
        onTertiary = Color.White,
    )

    val Light = lightColorScheme(
        primary = AspColor.Red,
        onPrimary = Color.White,
        secondary = AspColor.DarkRed,
        onSecondary = Color.White,
        tertiary = AspColor.DarkRed,
        onTertiary = Color.White,
    )
}
