package com.alejandro.proyecto_cines_frame.domain.validation

object CheckoutPaymentValidator {

    private val cardNumberRegex = Regex("^\\d{16}$")
    private val expiryRegex = Regex("^(0[1-9]|1[0-2])/(\\d{2}|\\d{4})$")
    private val cvvRegex = Regex("^\\d{3}$")

    fun validate(
        holder: String,
        cardNumber: String,
        expiry: String,
        cvv: String,
        email: String,
        confirmEmail: String,
        requireEmail: Boolean
    ): FieldErrors {
        val errors = mutableMapOf<String, MutableList<FieldError>>()

        if (holder.isBlank()) {
            errors.getOrPut("holder") { mutableListOf() }.add(FieldError.Required("holder"))
        }

        if (cardNumber.isBlank()) {
            errors.getOrPut("cardNumber") { mutableListOf() }.add(FieldError.Required("cardNumber"))
        } else if (!cardNumberRegex.matches(cardNumber)) {
            errors.getOrPut("cardNumber") { mutableListOf() }
                .add(FieldError.Custom("El numero de tarjeta solo puede contener 16 digitos"))
        }

        if (expiry.isBlank()) {
            errors.getOrPut("expiry") { mutableListOf() }.add(FieldError.Required("expiry"))
        } else if (!expiryRegex.matches(expiry)) {
            errors.getOrPut("expiry") { mutableListOf() }
                .add(FieldError.Custom("La fecha debe tener formato MM/AA o MM/AAAA"))
        }

        if (cvv.isBlank()) {
            errors.getOrPut("cvv") { mutableListOf() }.add(FieldError.Required("cvv"))
        } else if (!cvvRegex.matches(cvv)) {
            errors.getOrPut("cvv") { mutableListOf() }
                .add(FieldError.Custom("El CVV debe tener 3 digitos numericos"))
        }

        if (requireEmail) {
            val emailErrors = LoginValidator.validateEmail(email)
            if (emailErrors.isNotEmpty()) {
                errors.getOrPut("email") { mutableListOf() }.addAll(emailErrors)
            }

            val confirmEmailErrors = LoginValidator.validateEmail(confirmEmail)
            if (confirmEmailErrors.isNotEmpty()) {
                errors.getOrPut("confirmEmail") { mutableListOf() }.addAll(confirmEmailErrors)
            }

            if (emailErrors.isEmpty() && confirmEmailErrors.isEmpty() && email != confirmEmail) {
                errors.getOrPut("confirmEmail") { mutableListOf() }
                    .add(FieldError.Custom("El correo y su confirmacion deben coincidir"))
            }
        }

        return errors
    }
}

