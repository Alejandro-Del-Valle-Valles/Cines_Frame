package com.alejandro.proyecto_cines_frame.ui.components.filter.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.components.filter.FilterDay
import com.alejandro.proyecto_cines_frame.ui.components.filter.FilterState
// Botón del calendario que abre un desplegable para elegir día dioso
@Composable
fun CalendarButton(
    painter: Painter,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    days: List<FilterDay>,
    state: FilterState,
    onStateChange: (FilterState) -> Unit
) {
    Box {
        Image(
            painter = painter,
            contentDescription = "Calendario",
            modifier = Modifier
                .size(18.dp)
                .clickable { onExpandChange(true) }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandChange(false) }
        ) {
            days.forEach { day ->
                DropdownMenuItem(
                    text = { Text(day.label) },
                    onClick = {
                        onStateChange(state.copy(selectedDay = day))
                        onExpandChange(false)
                    }
                )
            }
        }
    }
}