package com.example.realapp.ui.attributes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.realapp.estimate.domain.model.AttributesData
import com.example.realapp.estimate.domain.model.ClaimInfoData

class AttributesViewModel :ViewModel() {
    val attributesData = MutableLiveData<AttributesData>()
    val claimInfoData = MutableLiveData<ClaimInfoData>()

    fun setAttributesData(data: AttributesData) {
        attributesData.value = data
    }

    fun setClaimInfoData(data: ClaimInfoData) {
        claimInfoData.value = data
    }
}
