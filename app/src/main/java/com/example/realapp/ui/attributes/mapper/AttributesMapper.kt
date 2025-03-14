package com.example.realapp.ui.attributes.mapper

import com.example.realapp.ui.attributes.mvi.AttributesState
import com.example.realapp.ui.attributes.mvi.AttributesViewState

class AttributesMapper constructor() {
    fun stateToViewState(state: AttributesState): AttributesViewState {
        return AttributesViewState(
            name = state.name,
            email = state.email,
            phoneNumber = state.phoneNumber,
            firstName = state.firstName,
            lastName = state.lastName,
            customerIsBusiness = state.customerIsBusiness,
            carrierName = state.carrierName,
            guidelines = state.guidelines
        )
    }
}
