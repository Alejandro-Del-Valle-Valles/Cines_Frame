package com.alejandro.proyecto_cines_frame.data.repository

import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.SalaApi
import com.alejandro.proyecto_cines_frame.domain.repository.SalaRepository

class SalaRepositoryImpl(
    private val api: SalaApi
) : SalaRepository {
}