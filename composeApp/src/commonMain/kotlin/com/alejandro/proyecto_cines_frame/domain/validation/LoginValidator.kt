package com.alejandro.proyecto_cines_frame.domain.validation

object LoginValidator {

    private val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    private val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")

    /**
     * Valida los campos de correo electrónico y contraseña. Devuelve un mapa con los errores encontrados para cada campo.
     */
    fun validate(correo: String, contrasena: String) : FieldErrors {
        val errors = mutableMapOf<String, List<FieldError>>()

        errors["correo"] = validateEmail(correo)
        errors["contrasena"] = validatePassword(contrasena)

        return errors
    }

    /**
     * Valida el correo electrónico. Debe ser obligatorio, no exceder los 100 caracteres y tener un formato válido de email.
     */
    fun validateEmail(correo: String) : List<FieldError> {
        val errors: MutableList<FieldError> = mutableListOf()

        if(correo.isBlank()) errors.add(FieldError.Required("correo"))
        else if (correo.length > 100) errors.add(FieldError.TooLong("correo", 100))
        else if (!emailRegex.matches(correo))
            errors.add(FieldError.InvalidFormat("correo", "email"))

        return errors
    }

    /**
     * Valida la contraseña. Debe ser obligatoria, tener entre 8 y 100 caracteres,
     * y contener al menos una letra, un número y un carácter especial.
     */
    fun validatePassword(contrasena: String) : List<FieldError> {
        val errors: MutableList<FieldError> = mutableListOf()

        if(contrasena.isBlank()) errors.add(FieldError.Required("contrasena"))
        else if (contrasena.length < 8) errors.add(FieldError.TooShort("contrasena", 8))
        else if (contrasena.length > 100) errors.add(FieldError.TooLong("contrasena", 100))
        else if (!passwordRegex.matches(contrasena))
            errors.add(
                FieldError.InvalidFormat(
                    "contrasena",
                    "Al menos una letra, un número y un carácter especial"
                )
            )

        return errors
    }
}