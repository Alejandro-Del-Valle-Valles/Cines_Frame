package com.alejandro.proyecto_cines_frame.ui.components.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

@Composable
fun CheckoutBottomBar(
    step: CheckoutStep,
    compactLayout: Boolean,
    onExit: () -> Unit,
    onPrevious: () -> Unit,
    onContinue: () -> Unit,
    continueEnabled: Boolean
) {
    if (step == CheckoutStep.PAYMENT) return

    val isFirstStep = step == CheckoutStep.SEATS
    val backLabel = if (isFirstStep) "Salir" else "Atrás"
    val continueLabel = if (step == CheckoutStep.SUMMARY) "Pagar" else "Continuar"

    Surface(
        tonalElevation = 2.dp,
        shadowElevation = 6.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.fillMaxWidth()) {
            HorizontalDivider()
            if (compactLayout) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = if (isFirstStep) onExit else onPrevious,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(backLabel)
                    }

                    Button(
                        onClick = onContinue,
                        enabled = continueEnabled,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(continueLabel)
                    }
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = if (isFirstStep) onExit else onPrevious,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(backLabel)
                    }

                    Button(
                        onClick = onContinue,
                        enabled = continueEnabled,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(continueLabel)
                    }
                }
            }
        }
    }
}