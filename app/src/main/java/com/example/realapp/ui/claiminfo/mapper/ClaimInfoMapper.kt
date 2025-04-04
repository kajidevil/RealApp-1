package com.example.realapp.ui.claiminfo.mapper

import com.example.realapp.ui.claiminfo.mvi.ClaimInfoState
import com.example.realapp.ui.claiminfo.mvi.ClaimInfoViewState
import javax.inject.Inject

class ClaimInfoMapper @Inject constructor() {
    fun stateToViewState(state: ClaimInfoState): ClaimInfoViewState {
        return ClaimInfoViewState(
            house = state.house,
            street = state.street,
            city = state.city,
            state = state.state,
            postalCode = state.postalCode,
        )
    }
}