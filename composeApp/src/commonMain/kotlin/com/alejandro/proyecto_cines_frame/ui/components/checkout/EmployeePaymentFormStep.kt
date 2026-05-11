package com.alejandro.proyecto_cines_frame.ui.components.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.theme.PrimaryRed

@Composable
fun EmployeePaymentFormStep(
    compactLayout: Boolean,
    modifier: Modifier = Modifier,
    formData: PaymentFormData,
    fieldErrors: Map<String, String>,
    generalError: String?,
    onEmailChange: (String) -> Unit,
    onConfirmEmailChange: (String) -> Unit,
    onBack: () -> Unit,
    onPayCash: () -> Unit,
    onPayCard: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .imePadding()
            .navigationBarsPadding()
            .padding(if (compactLayout) 16.dp else 28.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        Text(
            "Datos de contacto",
            style = if (compactLayout) MaterialTheme.typography.headlineSmall else MaterialTheme.typography.headlineMedium
        )

        Text(
            "Como empleado, selecciona el método de pago y proporciona el correo donde deseas recibir las entradas.",
            style = MaterialTheme.typography.bodyMedium
        )

        OutlinedTextField(
            value = formData.email,
            onValueChange = onEmailChange,
            label = { Text("Correo electrónico") },
            isError = fieldErrors.containsKey("email"),
            supportingText = {
                fieldErrors["email"]?.let { Text(it) }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = formData.confirmEmail,
            onValueChange = onConfirmEmailChange,
            label = { Text("Confirmar correo") },
            isError = fieldErrors.containsKey("confirmEmail"),
            supportingText = {
                fieldErrors["confirmEmail"]?.let { Text(it) }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.weight(1f))

        if (generalError != null) {
            Text(
                text = generalError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Text(
            "Selecciona el método de pago:",
            style = MaterialTheme.typography.bodyMedium
        )

        // Payment method buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onPayCash,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryRed)
            ) {
                Text(
                    "💶 Efectivo",
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            Button(
                onClick = onPayCard,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryRed)
            ) {
                Text(
                    "💳 Tarjeta",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Atrás")
        }
    }
}

