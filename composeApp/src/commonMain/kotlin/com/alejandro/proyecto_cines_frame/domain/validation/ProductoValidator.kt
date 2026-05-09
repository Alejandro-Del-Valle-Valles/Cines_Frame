package com.alejandro.proyecto_cines_frame.domain.validation

import com.alejandro.proyecto_cines_frame.domain.model.input.ProductoCreateInput

/**
 * Valida que un producto cumpla con las reglase para ser creado/actualizado, incluidos sus alérgenos
 */
object ProductoValidator {

    /**
     * Valida que un producto cumpla con las reglase para ser creado/actualizado, incluidos sus alérgenos
     */
    fun validateCreate(input: ProductoCreateInput): FieldErrors {
        val errors = mutableMapOf<String, List<FieldError>>()

        val nombreErrors = mutableListOf<FieldError>()
        val nombre = input.nombre.trim()
        if (nombre.isBlank()) nombreErrors.add(FieldError.Required("nombre"))
        else if (nombre.length > 50) nombreErrors.add(FieldError.TooLong("nombre", 25))
        if (nombreErrors.isNotEmpty()) errors["nombre"] = nombreErrors

        val precioErrors = mutableListOf<FieldError>()
        val precio = input.precio
        if (precio < 0) precioErrors.add(FieldError.Required("precio"))
        if (precioErrors.isNotEmpty()) errors["precio"] = precioErrors

        val stockErrors = mutableListOf<FieldError>()
        val stock = input.stock
        if (stock < 0) stockErrors.add(FieldError.Required("stock"))
        if (stockErrors.isNotEmpty()) errors["stock"] = stockErrors

        val alergenosErrors = mutableListOf<FieldError>()
        input.alergenos.forEach {
            val alergenoErrors = AlergenoValidator.validateCreate(it)
            if (alergenoErrors.isNotEmpty())
                alergenosErrors.add(
                    FieldError.Custom("Alergeno inválido: ${alergenoErrors.values.flatten().joinToString(", ")}")
                )
        }
        if (alergenosErrors.isNotEmpty()) errors["alergenos"] = alergenosErrors

        return errors
    }
}