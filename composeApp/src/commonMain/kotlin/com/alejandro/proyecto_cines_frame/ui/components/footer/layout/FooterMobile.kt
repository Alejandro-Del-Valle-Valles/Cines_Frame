package com.alejandro.proyecto_cines_frame.ui.components.footer.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.components.footer.FooterLink
import com.alejandro.proyecto_cines_frame.ui.components.footer.FooterSocialIcon
import com.alejandro.proyecto_cines_frame.ui.components.common.HeaderBrand
import org.jetbrains.compose.resources.painterResource
import proyecto_cines_frame.composeapp.generated.resources.Res
import proyecto_cines_frame.composeapp.generated.resources.facebook
import proyecto_cines_frame.composeapp.generated.resources.instagram
import proyecto_cines_frame.composeapp.generated.resources.x
//pata movil
@Composable
fun FooterMobile() {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HeaderBrand(esEscritorio = false)
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            FooterSocialIcon(
                painterResource(Res.drawable.facebook),
                "Facebook",
                "https://www.facebook.com"
            )
            FooterSocialIcon(
                painterResource(Res.drawable.instagram),
                "Instagram",
                "https://www.instagram.com"
            )
            FooterSocialIcon(
                painterResource(Res.drawable.x),
                "X",
                "https://www.x.com"
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            FooterLink("Centro de ayuda")
            FooterLink("Política de privacidad")
            FooterLink("Cookies")
        }
    }
}