package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageSessions

data class SessionFormState(
    val peliculaId: String = "",
    val peliculaNombre: String = "",
    val numSala: String = "",
    val fecha: String = "",
    val hora: String = "",
    val tresD: Boolean = false,
    val vose: Boolean = false
)