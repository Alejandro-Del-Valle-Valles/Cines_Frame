package com.alejandro.proyecto_cines_frame.domain.model.input

import com.alejandro.proyecto_cines_frame.domain.enums.ParticipanteRol

data class ParticipanteCreateInput(
    val id: Int?,
    val roles: List<ParticipanteRol> = emptyList()
)
