package com.alejandro.proyecto_cines_frame.ui.components.filter.layouts

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.components.common.DayChip
import com.alejandro.proyecto_cines_frame.ui.components.common.ToggleText
import com.alejandro.proyecto_cines_frame.ui.components.filter.FilterDay
import com.alejandro.proyecto_cines_frame.ui.components.filter.FilterState
import com.alejandro.proyecto_cines_frame.ui.components.filter.components.CalendarButton
import com.alejandro.proyecto_cines_frame.ui.theme.TextGray

@Composable
fun FilterMobile(
    state: FilterState,
    onStateChange: (FilterState) -> Unit,
    availableDays: List<FilterDay>,
    calendarDays: List<FilterDay>,
    calendarPainter: Painter,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            availableDays.forEach { day ->
                DayChip(day.label, state.selectedDay?.date == day.date) {
                    onStateChange(state.copy(selectedDay = day))
                }
            }

            CalendarButton(
                calendarPainter,
                expanded,
                onExpandChange,
                calendarDays,
                state,
                onStateChange
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

            ToggleText("2D", state.is3D == false) {
                val newValue = if (state.is3D == false) null else false
                onStateChange(state.copy(is3D = newValue))
            }

            Text("/", color = TextGray)

            ToggleText("3D", state.is3D == true) {
                val newValue = if (state.is3D == true) null else true
                onStateChange(state.copy(is3D = newValue))
            }

            ToggleText("VOSE", state.isVOSE) {
                onStateChange(state.copy(isVOSE = !state.isVOSE))
            }
        }
    }
}