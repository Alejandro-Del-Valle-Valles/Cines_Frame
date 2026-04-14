package com.alejandro.proyecto_cines_frame.data.remote.api.interfaces

import com.alejandro.proyecto_cines_frame.data.remote.dto.SalaDTO

interface SalaApi {
    suspend fun getAll() : List<SalaDTO>
    suspend fun getByNumero(numero: Int) : SalaDTO
    suspend fun createSala(sala: SalaDTO) : SalaDTO
    suspend fun updateSala(sala: SalaDTO) : SalaDTO
    suspend fun deleteSala(numero: Int) : SalaDTO
}