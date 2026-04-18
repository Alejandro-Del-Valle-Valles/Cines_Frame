package com.alejandro.proyecto_cines_frame.ui.components.header

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
//agrupa las acciones del header, como “Cartelera” y “Mi cuenta”, para no dejarlas sueltas por ahi, conclusion "Que me quedo sin comer"
@Composable
fun HeaderActions(
    onEntradasClick: () -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onLogoutClick: () -> Unit,
    isSessionActive: Boolean
) {
    var expanded by remember { mutableStateOf(false) }

    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {

        HeaderActionButton(
            texto = "Entradas",
            alClick = onEntradasClick
        )
        // Dropdown cuenta
        Box {
            HeaderActionButton(
                texto = "Mi cuenta",
                alClick = { expanded = true }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                if(!isSessionActive) {
                    DropdownMenuItem(
                        text = { Text("Iniciar sesión") },
                        onClick = {
                            expanded = false
                            onLoginClick()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Crear cuenta") },
                        onClick = {
                            expanded = false
                            onRegisterClick()
                        }
                    )
                } else {
                    DropdownMenuItem(
                        text = { Text("Cerrar sesión") },
                        onClick = {
                            expanded = false
                            onLogoutClick()
                        }
                    )
                }
            }
        }
    }
}