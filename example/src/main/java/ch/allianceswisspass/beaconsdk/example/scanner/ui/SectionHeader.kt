package ch.allianceswisspass.beaconsdk.example.scanner.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ch.allianceswisspass.beaconsdk.example.scanner.SectionState

@Composable
fun SectionHeader(
    modifier: Modifier = Modifier,
    text: String,
    itemCount: Int,
    sectionState: SectionState,
    onUpdateSectionState: (state: SectionState) -> Unit,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = when {
                    itemCount > 0 -> "$text ($itemCount)"
                    else -> text
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            )
            IconButton(
                onClick = {
                    when (sectionState) {
                        SectionState.Expanded -> onUpdateSectionState(SectionState.Collapsed)
                        else -> onUpdateSectionState(SectionState.Expanded)
                    }
                },
            ) {
                Icon(
                    imageVector = when (sectionState) {
                        SectionState.Expanded -> Icons.Default.KeyboardArrowDown
                        else -> Icons.Default.KeyboardArrowUp
                    },
                    contentDescription = null,
                )
            }
        }
    }
}
