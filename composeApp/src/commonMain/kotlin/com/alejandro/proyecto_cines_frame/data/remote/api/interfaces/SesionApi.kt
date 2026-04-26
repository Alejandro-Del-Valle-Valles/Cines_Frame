package com.alejandro.proyecto_cines_frame.data.remote.api.interfaces

import com.alejandro.proyecto_cines_frame.data.remote.dto.ButacasStatusResponse
import com.alejandro.proyecto_cines_frame.data.remote.dto.HoldButacaRequest
import com.alejandro.proyecto_cines_frame.data.remote.dto.HoldTokenResponse
import com.alejandro.proyecto_cines_frame.data.remote.dto.SesionCompletaDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.SesionCrudDTO
import io.ktor.http.HttpStatusCode

interface SesionApi {
    suspend fun getAll() : List<SesionCompletaDTO>
    suspend fun getByRangoHorario(inicio: String, fin: String) : List<SesionCompletaDTO>
    suspend fun getSesion(numSala: Int, peliculaId: String, horario: String) : SesionCompletaDTO
    suspend fun createSesion(sesion: SesionCrudDTO) : SesionCompletaDTO
    suspend fun deleteSesion(sesion: SesionCrudDTO) : SesionCompletaDTO
    suspend fun createHoldToken(numSala: Int, peliculaId: String, horario: String) : HoldTokenResponse
    suspend fun releaseHoldToken(numSala: Int, peliculaId: String, horario: String, token: String) : HttpStatusCode
    suspend fun holdButaca(numSala: Int, peliculaId: String, horario: String, req: HoldButacaRequest) : HttpStatusCode
    suspend fun releaseButaca(numSala: Int, peliculaId: String, horario: String, req: HoldButacaRequest) : HttpStatusCode
    suspend fun butacasStatus(numSala: Int, peliculaId: String, horario: String) : ButacasStatusResponse
}