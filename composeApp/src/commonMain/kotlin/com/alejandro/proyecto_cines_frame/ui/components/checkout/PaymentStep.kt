package com.alejandro.proyecto_cines_frame.ui.components.checkout

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun PaymentStep(
    onClose: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onClose,
        title = { Text("Compra completada") },
        text = {
            Text("Tus entradas han sido reservadas correctamente. Gracias por tu compra.")
        },
        confirmButton = {
            Button(onClick = onClose) {
                Text("Finalizar")
            }
        }
    )
}