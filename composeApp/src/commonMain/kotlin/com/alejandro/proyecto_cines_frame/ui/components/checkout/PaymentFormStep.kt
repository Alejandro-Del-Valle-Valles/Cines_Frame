package com.alejandro.proyecto_cines_frame.ui.components.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun PaymentFormStep(
    compactLayout: Boolean,
    modifier: Modifier = Modifier,
    formData: PaymentFormData,
    fieldErrors: Map<String, String>,
    generalError: String?,
    showEmailFields: Boolean,
    sessionEmail: String,
    onHolderChange: (String) -> Unit,
    onCardChange: (String) -> Unit,
    onExpiryChange: (String) -> Unit,
    onCvvChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onConfirmEmailChange: (String) -> Unit,
    onBack: () -> Unit,
    onPay: () -> Unit
) {

    val scrollState = rememberScrollState()

    var expiryField by remember {
        mutableStateOf(
            TextFieldValue(
                text = formData.expiry
            )
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .imePadding()
            .navigationBarsPadding()
            .padding(if (compactLayout) 16.dp else 28.dp),

        verticalArrangement =
            Arrangement.spacedBy(14.dp)
    ) {

        Text(
            "Datos de pago",

            style =
                if (compactLayout)
                    MaterialTheme.typography.headlineSmall
                else
                    MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = formData.holder,

            onValueChange = onHolderChange,

            label = {
                Text("Titular")
            },

            isError =
                fieldErrors.containsKey("holder"),

            supportingText = {

                fieldErrors["holder"]?.let {
                    Text(it)
                }
            },

            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = formData.cardNumber,

            onValueChange = onCardChange,

            label = {
                Text("Número de tarjeta")
            },

            isError =
                fieldErrors.containsKey("cardNumber"),

            supportingText = {

                fieldErrors["cardNumber"]?.let {
                    Text(it)
                }
            },

            keyboardOptions =
                KeyboardOptions(
                    keyboardType =
                        KeyboardType.Number
                ),

            modifier = Modifier.fillMaxWidth()
        )

        Row(
            horizontalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(

                value = expiryField,

                onValueChange = { value ->

                    val digits =
                        value.text
                            .filter { it.isDigit() }
                            .take(4)

                    val formatted = when {

                        digits.length <= 2 -> digits

                        else -> {

                            digits.substring(0, 2) +
                                    "/" +
                                    digits.substring(2)
                        }
                    }

                    expiryField = TextFieldValue(

                        text = formatted,

                        selection =
                            TextRange(formatted.length)
                    )

                    onExpiryChange(formatted)
                },

                label = {
                    Text("MM/AA")
                },

                isError =
                    fieldErrors.containsKey("expiry"),

                supportingText = {

                    fieldErrors["expiry"]?.let {
                        Text(it)
                    }
                },

                keyboardOptions =
                    KeyboardOptions(
                        keyboardType =
                            KeyboardType.Number
                    ),

                modifier =
                    Modifier.weight(1f)
            )

            OutlinedTextField(
                value = formData.cvv,

                onValueChange = onCvvChange,

                label = {
                    Text("CVV")
                },

                isError =
                    fieldErrors.containsKey("cvv"),

                supportingText = {

                    fieldErrors["cvv"]?.let {
                        Text(it)
                    }
                },

                keyboardOptions =
                    KeyboardOptions(
                        keyboardType =
                            KeyboardType.Number
                    ),

                modifier =
                    Modifier.weight(1f)
            )
        }

        if (showEmailFields) {

            Text(
                "Datos de contacto",

                style =
                    if (compactLayout)
                        MaterialTheme.typography.headlineSmall
                    else
                        MaterialTheme.typography.headlineMedium
            )

            OutlinedTextField(
                value = formData.email,

                onValueChange =
                    onEmailChange,

                label = {
                    Text("Correo")
                },

                isError =
                    fieldErrors.containsKey("email"),

                supportingText = {

                    fieldErrors["email"]?.let {
                        Text(it)
                    }
                },

                keyboardOptions =
                    KeyboardOptions(
                        keyboardType =
                            KeyboardType.Email
                    ),

                modifier =
                    Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = formData.confirmEmail,

                onValueChange =
                    onConfirmEmailChange,

                label = {
                    Text("Confirmacion de Correo")
                },

                isError =
                    fieldErrors.containsKey("confirmEmail"),

                supportingText = {

                    fieldErrors["confirmEmail"]?.let {
                        Text(it)
                    }
                },

                keyboardOptions =
                    KeyboardOptions(
                        keyboardType =
                            KeyboardType.Email
                    ),

                modifier =
                    Modifier.fillMaxWidth()
            )

        } else {

            Text(
                text =
                    "Enviaremos las entradas a: $sessionEmail",

                style =
                    MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(
            Modifier.weight(1f)
        )

        if (generalError != null) {

            Text(
                text = generalError,

                color =
                    MaterialTheme.colorScheme.error,

                style =
                    MaterialTheme.typography.bodyMedium
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {

            OutlinedButton(
                onClick = onBack,

                modifier =
                    Modifier.weight(1f)
            ) {

                Text("Atrás")
            }

            Button(
                onClick = onPay,

                modifier =
                    Modifier.weight(1f)
            ) {

                Text("Pagar")
            }
        }
    }
}

private fun formatExpiryDate(
    input: String
): String {

    val digits = buildString {

        input.forEach { c ->

            if (c.isDigit()) {
                append(c)
            }
        }
    }.take(4)

    return when {

        digits.length <= 2 -> digits

        else -> {
            "${digits.substring(0, 2)}/${digits.substring(2)}"
        }
    }
}
