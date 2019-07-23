package com.eyulingo.ui.register

data class RegisterFormState (
    val emailError: Int? = null,
    val confirmError: Int? = null,
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val passwordConfirmError: Int? = null,
    val isSendConfirmValid: Boolean = false,
    val isDataValid: Boolean = false
)