package com.alejandro.proyecto_cines_frame.data.remote.api.interfaces

import com.alejandro.proyecto_cines_frame.data.remote.dto.ParticipanteDTO

interface ParticipanteApi {
    suspend fun getAll() : List<ParticipanteDTO>
    suspend fun getAllByNombre(nombre: String) : List<ParticipanteDTO>
    suspend fun getById(id: Int) : ParticipanteDTO
    suspend fun createParticipante(nombre: String) : ParticipanteDTO
    suspend fun updateParticipante(participante: ParticipanteDTO) : ParticipanteDTO
    suspend fun deleteParticipante(id: Int) : ParticipanteDTO
}