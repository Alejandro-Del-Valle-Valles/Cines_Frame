package com.alejandro.proyecto_cines_frame.data.remote.api.interfaces

import com.alejandro.proyecto_cines_frame.data.remote.dto.SesionCompletaDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.SesionCrudDTO

interface SesionApi {
    suspend fun getAll() : List<SesionCompletaDTO>
    suspend fun getByRangoHorario(inicio: String, fin: String) : List<SesionCompletaDTO>
    suspend fun getSesion(numSala: Int, peliculaId: String, horario: String) : SesionCompletaDTO
    suspend fun createSesion(sesion: SesionCrudDTO) : SesionCompletaDTO
    suspend fun deleteSesion(sesion: SesionCrudDTO) : SesionCompletaDTO
}