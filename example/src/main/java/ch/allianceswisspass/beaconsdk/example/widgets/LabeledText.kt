package ch.allianceswisspass.beaconsdk.example.widgets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun LabeledText(label: String, text: String, padding: PaddingValues = PaddingValues(all = 0.dp)) {
    val paddingStart = padding.calculateStartPadding(LocalLayoutDirection.current)
    val paddingTop = padding.calculateTopPadding()
    val paddingEnd = padding.calculateEndPadding(LocalLayoutDirection.current)
    val paddingBottom = padding.calculateBottomPadding()
    Text(
        text = label,
        fontWeight = FontWeight.Light,
        style = MaterialTheme.typography.labelSmall,
        modifier = Modifier.padding(
            start = paddingStart,
            top = paddingTop,
            end = paddingEnd,
        ),
    )
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(
            start = paddingStart,
            end = paddingEnd,
            bottom = paddingBottom,
        ),
    )
}
