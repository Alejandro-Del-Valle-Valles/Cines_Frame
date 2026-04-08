package com.alejandro.proyecto_cines_frame.ui.components.header.filter

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.components.header.HeaderUtils
import com.alejandro.proyecto_cines_frame.ui.theme.SurfaceDark
import com.alejandro.proyecto_cines_frame.ui.theme.TextGray
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite
import org.jetbrains.compose.resources.painterResource
import proyecto_cines_frame.composeapp.generated.resources.Res
import proyecto_cines_frame.composeapp.generated.resources.search
//Para buscar pelis pelosas
@Composable
fun HeaderSearchField(
    valor: String,
    alCambiarValor: (String) -> Unit,
    alBuscar: (String) -> Unit,
    alCambiarFoco: (Boolean) -> Unit,
    placeholder: String,
    esCompacto: Boolean
) {
    val forma = RoundedCornerShape(10.dp)

    Surface(
        color = SurfaceDark,
        shape = forma,
        modifier = Modifier
            .height(if (esCompacto) HeaderUtils.AltoBuscadorMovil else HeaderUtils.AltoBuscadorEscritorio)
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
                    contentDescription = "Buscar",
                    tint = TextGray,
                    modifier = Modifier.clickable {
                        if (valor.length >= 3) {
                            alBuscar(valor)
                        }
                    }
                )
            },

            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),

            keyboardActions = KeyboardActions(
                onSearch = {
                    if (valor.length >= 3) {
                        alBuscar(valor)
                    }
                }
            ),

            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = TextWhite,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
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