package com.alejandro.proyecto_cines_frame.ui.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.components.common.BackButton
import com.alejandro.proyecto_cines_frame.ui.logic.presenter.LoginPresenter
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    presenter: LoginPresenter
) {
    val state by presenter.state.collectAsState()
    //TODO: Cambiar esto por un toast/snackbar o lo que sea
    var showSuccessMessage by remember { mutableStateOf(false) }

    LaunchedEffect(state.loginSuccess) {
        if (state.loginSuccess) {
            showSuccessMessage = true
            delay(1000)
            presenter.consumeLoginSuccess()
            onLoginSuccess()
        }
    }

    val cardColor = Color(0xFF1E1E1E)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark),
        contentAlignment = Alignment.Center
    ) {
        BackButton(
            onClick = {
                onLoginSuccess() // vuelve al main
            }
        )
        Card(
            modifier = Modifier.fillMaxWidth(0.9f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = cardColor
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Iniciar sesión",
                    color = TextWhite,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Email
                OutlinedTextField(
                    value = state.correo,
                    onValueChange = presenter::onCorreoChange,
                    label = { Text("Email", color = TextWhite) },
                    isError = state.fieldErrors.containsKey("correo"),
                    supportingText = {
                        val err = state.fieldErrors["correo"]
                        if(err != null) Text(err, color = Color.Red)
                    },
                    textStyle = LocalTextStyle.current.copy(color = TextWhite),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.DarkGray,
                        cursorColor = TextWhite
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Password
                OutlinedTextField(
                    value = state.contrasena,
                    onValueChange = presenter::onContrasenaChange,
                    label = { Text("Contraseña", color = TextWhite) },
                    isError = state.fieldErrors.containsKey("contrasena"),
                    supportingText = {
                        val err = state.fieldErrors["contrasena"]
                        if(err != null) Text(err, color = Color.Red)
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    textStyle = LocalTextStyle.current.copy(color = TextWhite),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.DarkGray,
                        cursorColor = TextWhite
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (state.generalError != null) {
                    Text(
                        text = state.generalError!!,
                        color = Color.Red
                    )
                }

                if (showSuccessMessage) {
                    Text(
                        text = "Inicio de sesion correcto",
                        color = Color(0xFF4CAF50)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón de login
                Button(
                    onClick = { presenter.submit(rememberMe = false) },
                    enabled = !state.isLoading,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE50914),
                        contentColor = TextWhite
                    )
                ) {
                    Text(
                        text = if (state.isLoading) "Cargando..." else "Iniciar sesión"
                    )
                }
            }
        }
    }
}
