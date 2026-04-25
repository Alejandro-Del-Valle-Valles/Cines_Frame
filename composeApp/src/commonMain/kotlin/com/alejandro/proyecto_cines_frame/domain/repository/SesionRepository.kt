package com.alejandro.proyecto_cines_frame.domain.repository

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.remote.dto.ButacasStatusResponse
import com.alejandro.proyecto_cines_frame.data.remote.dto.HoldButacaRequest
import com.alejandro.proyecto_cines_frame.data.remote.dto.SesionCrudDTO
import com.alejandro.proyecto_cines_frame.domain.model.HoldToken
import com.alejandro.proyecto_cines_frame.domain.model.Sesion

interface SesionRepository {
    suspend fun getAll() : ApiResult<List<Sesion>>
    suspend fun getByRangoHorario(inicio: String, fin: String) : ApiResult<List<Sesion>>
    suspend fun getSesion(numSala: Int, peliculaId: String, horario: String) : ApiResult<Sesion>
    suspend fun createSesion(sesion: SesionCrudDTO) : ApiResult<Sesion>
    suspend fun deleteSesion(sesion: SesionCrudDTO) : ApiResult<Sesion>
    suspend fun createHoldToken(numSala: Int, peliculaId: String, horario: String) : ApiResult<HoldToken>
    suspend fun holdButaca(numSala: Int, peliculaId: String, horario: String, req: HoldButacaRequest) : ApiResult<Unit>
    suspend fun releaseButaca(numSala: Int, peliculaId: String, horario: String, req: HoldButacaRequest) : ApiResult<Unit>
    suspend fun butacasStatus(numSala: Int, peliculaId: String, horario: String) : ApiResult<ButacasStatusResponse>
}