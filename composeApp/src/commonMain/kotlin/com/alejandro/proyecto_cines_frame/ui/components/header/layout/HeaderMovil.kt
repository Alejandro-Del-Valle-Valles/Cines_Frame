package com.alejandro.proyecto_cines_frame.ui.components.header.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.components.header.HeaderActions
import com.alejandro.proyecto_cines_frame.ui.components.common.HeaderBrand
import com.alejandro.proyecto_cines_frame.ui.components.header.filter.HeaderSearchField

//header movil
@Composable
fun HeaderMovil(
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
    Column(modifier = Modifier.fillMaxWidth()) {
        HeaderBrand(esEscritorio = false)
        Spacer(Modifier.height(14.dp))
        Row {
            Box(modifier = Modifier.weight(1f)) {
                HeaderSearchField(
                    valor = searchQuery,
                    alCambiarValor = onSearchChange,
                    alBuscar = onSearchSubmit,
                    alCambiarFoco = onFocusChange,
                    placeholder = "Buscar",
                    esCompacto = true
                )
            }
            Spacer(Modifier.width(16.dp))
            HeaderActions(
                onEntradasClick,
                onLoginClick,
                onRegisterClick,
                onLogoutClick,
                isSessionActive
            )
        }
    }
}