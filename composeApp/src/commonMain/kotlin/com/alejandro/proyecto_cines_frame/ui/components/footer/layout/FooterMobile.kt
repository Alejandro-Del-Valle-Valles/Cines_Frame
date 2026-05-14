package com.alejandro.proyecto_cines_frame.ui.components.footer.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.components.common.HeaderBrand
import com.alejandro.proyecto_cines_frame.ui.components.footer.FooterInfoDialog
import com.alejandro.proyecto_cines_frame.ui.components.footer.FooterLink
import com.alejandro.proyecto_cines_frame.ui.components.footer.FooterSocialIcon
import org.jetbrains.compose.resources.painterResource
import proyecto_cines_frame.composeapp.generated.resources.*

@Composable
fun FooterMobile() {

    var dialogTitle by remember {
        mutableStateOf<String?>(null)
    }

    var dialogContent by remember {
        mutableStateOf("")
    }

    fun openDialog(
        title: String,
        content: String
    ) {
        dialogTitle = title
        dialogContent = content
    }

    Column(

        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment =
            Alignment.CenterHorizontally,
        verticalArrangement =
            Arrangement.spacedBy(12.dp)
    ) {

        HeaderBrand(
            esEscritorio = false
        )
        Row(
            horizontalArrangement =
                Arrangement.spacedBy(16.dp)
        ) {
            FooterSocialIcon(
                painterResource(
                    Res.drawable.facebook
                ),
                "Facebook",
                "https://www.facebook.com"
            )

            FooterSocialIcon(
                painterResource(
                    Res.drawable.instagram
                ),
                "Instagram",
                "https://www.instagram.com"
            )

            FooterSocialIcon(
                painterResource(
                    Res.drawable.x
                ),
                "X",
                "https://www.x.com"
            )
        }

        Column(
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {
            FooterLink(
                text = "Centro de ayuda",
                onClick = {
                    openDialog(
                        "Centro de ayuda",
                        "Si necesitas ayuda con compras, sesiones o problemas técnicos puedes contactar con soporte."
                    )
                }
            )

            FooterLink(
                text = "Política de privacidad",
                onClick = {
                    openDialog(
                        "Política de privacidad",
                        "Tus datos se usan únicamente para la gestión de la aplicación y compras."
                    )
                }
            )

            FooterLink(
                text = "Cookies",
                onClick = {
                    openDialog(
                        "Cookies",
                        "Usamos cookies técnicas necesarias para el funcionamiento de la aplicación."
                    )
                }
            )
        }
    }

    if (dialogTitle != null) {

        FooterInfoDialog(
            title = dialogTitle!!,
            content = dialogContent,
            onDismiss = {
                dialogTitle = null
            }
        )
    }
}