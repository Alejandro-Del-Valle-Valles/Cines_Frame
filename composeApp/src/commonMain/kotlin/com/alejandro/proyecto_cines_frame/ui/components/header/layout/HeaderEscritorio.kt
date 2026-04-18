package com.alejandro.proyecto_cines_frame.ui.components.header.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.components.header.HeaderActions
import com.alejandro.proyecto_cines_frame.ui.components.common.HeaderBrand
import com.alejandro.proyecto_cines_frame.ui.components.header.HeaderUtils
import com.alejandro.proyecto_cines_frame.ui.components.header.filter.HeaderSearchField

//Header de escritorio
@Composable
fun HeaderEscritorio(
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onSearchSubmit: (String) -> Unit,
    onFocusChange: (Boolean) -> Unit,

    onEntradasClick: () -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onLogoutClick: () -> Unit,
    isSessionActive: Boolean
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        HeaderBrand(
            esEscritorio = true,
            modifier = Modifier.weight(1f)
        )
        Box(
            modifier = Modifier.width(HeaderUtils.AnchoMaximoBuscadorEscritorio)
        ) {
            HeaderSearchField(
                valor = searchQuery,
                alCambiarValor = onSearchChange,
                alBuscar = onSearchSubmit,
                alCambiarFoco = onFocusChange,
                placeholder = "Buscar películas",
                esCompacto = false
            )
        }
        Spacer(Modifier.width(24.dp))
        HeaderActions(
            onEntradasClick,
            onLoginClick,
            onRegisterClick,
            onLogoutClick,
            isSessionActive
        )
    }
}