package com.alejandro.proyecto_cines_frame.ui.components.session

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SessionBadges(
    is3D: Boolean,
    isVose: Boolean,
    modifier: Modifier = Modifier
) {
    val badges = buildList {
        if (is3D) {
            add("🥽")
        } else if (!isVose) {
            add("🎞️")
        }

        if (isVose) {
            add("🌍")
        }
    }

    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        badges.forEach { label ->
            AssistChip(
                onClick = { },
                label = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                shape = RoundedCornerShape(999.dp),
                colors = AssistChipDefaults.assistChipColors()
            )
        }
    }
}