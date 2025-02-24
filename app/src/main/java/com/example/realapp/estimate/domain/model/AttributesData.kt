package com.example.realapp.estimate.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AttributesData(
    val projectManager: ProjectManagerData?,
    val customerData: CustomerData?,
    val carrierData: CarrierData?
) : Parcelable

@Parcelize
data class ClaimInfoData(
    val adress: AddressData?
) : Parcelable
