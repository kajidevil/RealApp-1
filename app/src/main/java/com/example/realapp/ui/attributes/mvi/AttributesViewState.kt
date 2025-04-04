package com.example.realapp.ui.attributes.mvi

data class AttributesViewState(
    val name: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val customerIsBusiness: Boolean = false,
    val carrierName: String = "",
    val guidelines: String = "",
    val isLoading: Boolean = false
)