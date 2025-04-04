package com.example.realapp.ui.claiminfo.mvi

data class ClaimInfoState(
    val isLoading: Boolean = false,
    val isGpsEnabled: Boolean = false,  // Включен ли GPS
    val isLocationPermissionGranted: Boolean = false, // Есть ли разрешение на локацию
    val house: String = "",
    val street: String = "",
    val city: String = "",
    val state: String = "",
    val postalCode: String = "",
)