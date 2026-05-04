package com.alejandro.proyecto_cines_frame.domain.validation

object CuentaValidator {

    private val maxLengthName = 50

    /**
     * Valida que el nuevo nombre cumpla el formato y no sea igual al actual.
     */
    fun validateNewName(currentName: String, newName: String): FieldErrors {
        val errors = mutableMapOf<String, List<FieldError>>()

        if (newName.isBlank())
            errors["nombre"] = listOf(FieldError.Required("nombre"))
        else if (newName.length > maxLengthName)
            errors["nombre"] = listOf(FieldError.TooLong("nombre", maxLengthName))
        else if (newName == currentName)
            errors["nombre"] = listOf(FieldError.Custom("El nombre no puede ser igual al actual"))

        return errors
    }

    /**
     * Valida que la nuevas contraseñas cumplan con el formato correcto y no sean iguales a la actual.
     */
    fun validateNewPassword(currentPassword: String, newPassword: String, confirmNewPassword: String): FieldErrors {
        val errors = mutableMapOf<String, List<FieldError>>()

        errors["contrasenaIgual"] = validateNewPasswordIsNotEqualCurrentPassword(newPassword, currentPassword)
        errors["contrasena"] = LoginValidator.validatePassword(newPassword)
        errors["confirmarContrasena"] = validateConfirmPassword(newPassword, confirmNewPassword)

        return errors
    }

    /**
     * Valida que la nueva contraseña no sea igual a la contraseña actual.
     */
    private fun validateNewPasswordIsNotEqualCurrentPassword(newPassword: String, currentPassword: String): List<FieldError> {
        val errors: MutableList<FieldError> = mutableListOf()

        if (newPassword == currentPassword)
            errors.add(FieldError.Custom("La nueva contraseña no puede ser igual a la actual"))

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