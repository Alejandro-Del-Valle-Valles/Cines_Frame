package com.alejandro.proyecto_cines_frame.data.repository

import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.ParticipanteApi
import com.alejandro.proyecto_cines_frame.domain.repository.ParticipanteRepository

class ParticipanteRepositoryImpl(
    private val api: ParticipanteApi
) : ParticipanteRepository {
}