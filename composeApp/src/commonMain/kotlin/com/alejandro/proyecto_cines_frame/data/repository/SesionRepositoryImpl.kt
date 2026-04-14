package com.alejandro.proyecto_cines_frame.data.repository

import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.SesionApi
import com.alejandro.proyecto_cines_frame.domain.repository.SesionRepository

class SesionRepositoryImpl(
    private val api: SesionApi
) : SesionRepository {
}