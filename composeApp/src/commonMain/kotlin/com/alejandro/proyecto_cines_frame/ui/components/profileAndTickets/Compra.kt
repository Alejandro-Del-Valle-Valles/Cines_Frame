package com.alejandro.proyecto_cines_frame.ui.components.profileAndTickets

import kotlinx.datetime.LocalDate

data class Compra(
    val correo: String //,
    //val tipoEntrada: String
) {
    var fecha = LocalDate(2023, 10, 1)
    var tituloPelicula = "Titulo de la película"
    var cantidadEntradas = 1
    var tipoEntrada= "Adulto"
    var precioTotal = 2f

    private fun tipoEntradas (tipoEntrada: String): Float {
        if (tipoEntrada == "Adulto") {
            return 8.5f
        } else if (tipoEntrada == "Nino"){
            return 6.0f
        } else {
            return 7.0f
        }
    }
}