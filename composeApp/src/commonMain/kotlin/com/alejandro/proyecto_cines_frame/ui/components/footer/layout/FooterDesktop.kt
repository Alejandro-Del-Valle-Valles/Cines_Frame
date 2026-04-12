package com.alejandro.proyecto_cines_frame.ui.components.footer.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
//Footer para escritorio
@Composable
fun FooterDesktop() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HeaderBrand(esEscritorio = true)
        Spacer(modifier = Modifier.weight(1f))
        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FooterLink("Centro de ayuda")
            FooterLink("Política de privacidad")
            FooterLink("Cookies")
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FooterSocialIcon(
                painter = painterResource(Res.drawable.facebook),
                contentDescription = "Facebook",
                url = "https://www.facebook.com"
            )
            FooterSocialIcon(
                painter = painterResource(Res.drawable.instagram),
                contentDescription = "Instagram",
                url = "https://www.instagram.com"
            )
            FooterSocialIcon(
                painter = painterResource(Res.drawable.x),
                contentDescription = "X",
                url = "https://www.x.com"
            )
        }
    }
}