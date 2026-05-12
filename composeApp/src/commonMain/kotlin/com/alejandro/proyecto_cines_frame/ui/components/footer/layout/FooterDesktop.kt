package com.alejandro.proyecto_cines_frame.ui.components.footer.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
fun FooterDesktop() {

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

    Row(
        modifier = Modifier.fillMaxWidth(),

        verticalAlignment =
            Alignment.CenterVertically
    ) {

        HeaderBrand(
            esEscritorio = true
        )

        Spacer(
            modifier = Modifier.weight(1f)
        )

        Row(
            horizontalArrangement =
                Arrangement.spacedBy(32.dp),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            FooterLink(
                text = "Centro de ayuda",
                onClick = {
                    openDialog(
                        "Centro de ayuda",

                        "Si necesitas ayuda con la compra de entradas, problemas técnicos o gestión de tu cuenta, puedes contactar con soporte en soporte@cinesframes.com.\n\nHorario de atención:\nLunes a domingo de 10:00 a 22:00."
                    )
                }
            )

            FooterLink(
                text = "Política de privacidad",
                onClick = {
                    openDialog(
                        "Política de privacidad",

                        "Cines Frames recopila únicamente los datos necesarios para gestionar cuentas, compras y sesiones.\n\nNo compartimos información personal con terceros salvo obligación legal."
                    )
                }
            )

            FooterLink(
                text = "Cookies",
                onClick = {
                    openDialog(
                        "Cookies",

                        "Esta aplicación utiliza cookies técnicas y de sesión para mejorar la experiencia de usuario y mantener la autenticación activa."
                    )
                }
            )
        }

        Spacer(
            modifier = Modifier.weight(1f)
        )

        Row(
            horizontalArrangement =
                Arrangement.spacedBy(16.dp)
        ) {

            FooterSocialIcon(
                painter =
                    painterResource(
                        Res.drawable.facebook
                    ),
                contentDescription =
                    "Facebook",
                url =
                    "https://www.facebook.com"
            )

            FooterSocialIcon(
                painter =
                    painterResource(
                        Res.drawable.instagram
                    ),
                contentDescription =
                    "Instagram",
                url =
                    "https://www.instagram.com"
            )

            FooterSocialIcon(
                painter =
                    painterResource(
                        Res.drawable.x
                    ),
                contentDescription =
                    "X",
                url =
                    "https://www.x.com"
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