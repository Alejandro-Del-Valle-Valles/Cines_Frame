package com.alejandro.proyecto_cines_frame.ui.screen.management

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Sala
import com.alejandro.proyecto_cines_frame.ui.components.admin.RoomManagementScreens.RoomManagementDesktop
import com.alejandro.proyecto_cines_frame.ui.components.admin.RoomManagementScreens.RoomManagementMovile
import com.alejandro.proyecto_cines_frame.ui.components.common.BackButton
import com.alejandro.proyecto_cines_frame.ui.components.footer.FooterUtils
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader

@Composable
fun RoomManagementScreen(
    rooms: List<Sala>,
    onAddRoom: () -> Unit,
    onEditRoom: (Sala) -> Unit,
    onDeleteRoom: (Sala) -> Unit,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BackButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .zIndex(10f)
                .padding(16.dp)
        )
        BoxWithConstraints (
            modifier = Modifier
                .fillMaxWidth()
                .background(ColorFondoHeader)
                .padding(16.dp)
        ) {

            val esEscritorio = FooterUtils.esEscritorio(maxWidth)

            if (esEscritorio) {
                RoomManagementDesktop(rooms, onAddRoom, onEditRoom, onDeleteRoom)
            } else {
                RoomManagementMovile(rooms, onAddRoom, onEditRoom, onDeleteRoom)
            }
        }
    }
}