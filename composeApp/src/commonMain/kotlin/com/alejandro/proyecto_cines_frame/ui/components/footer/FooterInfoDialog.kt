package com.alejandro.proyecto_cines_frame.ui.components.footer

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FooterInfoDialog(
    title: String,
    content: String,
    onDismiss: () -> Unit
) {

    AlertDialog(

        onDismissRequest = onDismiss,
        title = {
            Text(title)
        },

        text = {

            Text(
                text = content,
                modifier = Modifier
                    .verticalScroll(
                        rememberScrollState()
                    )
            )
        },

        confirmButton = {

            TextButton(
                onClick = onDismiss
            ) {
                Text("Cerrar")
            }
        }
    )
}