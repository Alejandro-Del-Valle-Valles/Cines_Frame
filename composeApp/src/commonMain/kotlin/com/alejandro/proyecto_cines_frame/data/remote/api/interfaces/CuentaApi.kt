package com.alejandro.proyecto_cines_frame.data.remote.api.interfaces

import com.alejandro.proyecto_cines_frame.data.remote.dto.CuentaDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.CuentaLoginDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.CuentaUpdateDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.LoginDTO

interface CuentaApi {
    suspend fun login(login: LoginDTO) : CuentaLoginDTO
    fun logout()
    suspend fun createCuenta(cuenta: CuentaDTO) : CuentaDTO
    suspend fun updateCuenta(cuenta: CuentaUpdateDTO) : CuentaDTO
    suspend fun deleteCuenta() : CuentaDTO
}