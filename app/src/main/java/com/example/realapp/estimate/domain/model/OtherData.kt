package com.example.realapp.estimate.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize // 🔥 ОБЯЗАТЕЛЬНО ДОБАВЬ

@Parcelize
data class ProjectManagerData(
    val firstName: String,
    val email: String,
    val lastName: String,
    val phoneNumber: String
) : Parcelable

@Parcelize
data class CustomerData(
    val firstName: String,
    val lastName: String,
    val customerIsBusiness: Boolean
) : Parcelable

@Parcelize
data class CarrierData(
    val carrierName: String,
    val carrierGuideLines: String?
) : Parcelable

@Parcelize
data class AddressData(
    val houseName: String,
    val streetName: String,
    val cityName: String,
    val stateName: String,
    val postalCodeName: String
) : Parcelable
