package com.alejandro.proyecto_cines_frame.ui.components.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.components.filter.layouts.FilterDesktop
import com.alejandro.proyecto_cines_frame.ui.components.filter.layouts.FilterMobile
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader
//Contenedor principal de filtros que decide si pinta versión movil o escritorio, segun el ancho disponible
@Composable
fun Filter(
    state: FilterState,
    onStateChange: (FilterState) -> Unit,
    availableDays: List<FilterDay>,
    calendarDays: List<FilterDay>,
    calendarPainter: Painter
) {
    var expanded by remember { mutableStateOf(false) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(50.dp))
            .background(ColorFondoHeader.copy(alpha = 0.75f))
            .padding(vertical = 10.dp)
    ) {

        val isDesktop = FilterLayoutUtils.esEscritorio(maxWidth)

        if (isDesktop) {
            FilterDesktop(
                state = state,
                onStateChange = onStateChange,
                availableDays = availableDays,
                calendarDays = calendarDays,
                calendarPainter = calendarPainter,
                expanded = expanded,
                onExpandChange = { expanded = it }
            )
        } else {
            FilterMobile(
                state = state,
                onStateChange = onStateChange,
                availableDays = availableDays,
                calendarDays = calendarDays,
                calendarPainter = calendarPainter,
                expanded = expanded,
                onExpandChange = { expanded = it }
            )
        }
    }
}
