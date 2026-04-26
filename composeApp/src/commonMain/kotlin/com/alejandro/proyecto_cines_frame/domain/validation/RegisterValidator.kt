package com.alejandro.proyecto_cines_frame.domain.validation

object RegisterValidator {

    /**
     * Valida los campos de nombre, correo electrónico, contraseña y confirmación de contraseña.
     * Devuelve un mapa con los errores encontrados para cada campo.
     */
    fun validate(nombre: String, correo: String, contrasena: String, confirmarContrasena: String) : FieldErrors {
        val errors = mutableMapOf<String, List<FieldError>>()

        errors["nombre"] = validateName(nombre)
        errors["correo"] = LoginValidator.validateEmail(correo)
        errors["contrasena"] = LoginValidator.validatePassword(contrasena)
        errors["confirmarContrasena"] = validateConfirmPassword(contrasena, confirmarContrasena)

        return errors
    }

    /**
     * Valida el nombre. Debe ser obligatorio y no exceder los 50 caracteres.
     */
    private fun validateName(nombre: String) : List<FieldError> {
        val errors: MutableList<FieldError> = mutableListOf()

        if(nombre.isBlank()) errors.add(FieldError.Required("nombre"))
        else if (nombre.length > 50) errors.add(FieldError.TooLong("nombre", 50))

        return errors
    }

    /**
     * Valida la confirmación de contraseña. Debe ser obligatoria y coincidir con la contraseña ingresada.
     */
    private fun validateConfirmPassword(contrasena: String, confirmarContrasena: String) : List<FieldError> {
        val errors: MutableList<FieldError> = mutableListOf()

        if(confirmarContrasena.isBlank()) errors.add(FieldError.Required("confirmarContrasena"))
        else if (confirmarContrasena != contrasena)
            errors.add(FieldError.Custom("Las contraseñas no coinciden"))

        return errors
    }
}