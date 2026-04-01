package com.alejandro.proyecto_cines_frame.ui.components.header

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.theme.SurfaceDark
import com.alejandro.proyecto_cines_frame.ui.theme.TextGray
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite
import org.jetbrains.compose.resources.painterResource
import proyecto_cines_frame.composeapp.generated.resources.Res
import proyecto_cines_frame.composeapp.generated.resources.search

@Composable
fun HeaderSearchField(
    valor: String,
    alCambiarValor: (String) -> Unit,
    alCambiarFoco: (Boolean) -> Unit,
    placeholder: String,
    esCompacto: Boolean
) {
    val forma = RoundedCornerShape(10.dp)

    Surface(
        color = SurfaceDark,
        shape = forma,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        modifier = Modifier
            .height(if (esCompacto) HeaderUtils.AltoBuscadorMovil else HeaderUtils.AltoBuscadorEscritorio)
            .border(
                width = 0.dp,
                color = Color.Transparent,
                shape = forma
            )
    ) {
        TextField(
            value = valor,
            onValueChange = alCambiarValor,
            singleLine = true,
            placeholder = {
                Text(
                    text = placeholder,
                    color = TextGray.copy(alpha = 0.75f)
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(Res.drawable.search),
                    contentDescription = null,
                    tint = TextGray
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                cursorColor = TextWhite,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite
            ),
            textStyle = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxSize()
                .onFocusChanged { alCambiarFoco(it.isFocused) }
        )
    }
}