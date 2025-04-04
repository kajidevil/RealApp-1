package com.example.realapp.ui.claiminfo.mvi

data class ClaimInfoViewState (
    val isLoading: Boolean = false,
    val house: String = "",
    val street: String = "",
    val city: String = "",
    val state: String = "",
    val postalCode: String = "",

)