package com.example.realapp.ui.claiminfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realapp.ui.claiminfo.mapper.ClaimInfoMapper
import com.example.realapp.ui.claiminfo.mvi.ClaimInfoAction
import com.example.realapp.ui.claiminfo.mvi.ClaimInfoSideEffect
import com.example.realapp.ui.claiminfo.mvi.ClaimInfoState
import com.example.realapp.ui.claiminfo.mvi.ClaimInfoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject

@HiltViewModel
class ClaimInfoViewModel @Inject constructor(
    private val mapper: ClaimInfoMapper
) : ViewModel(), ContainerHost<ClaimInfoViewState, ClaimInfoSideEffect> {

    private var vmState: ClaimInfoState = ClaimInfoState()

    override val container =
        viewModelScope.container<ClaimInfoViewState, ClaimInfoSideEffect>(
            mapper.stateToViewState(vmState)
        )

    fun action(action: ClaimInfoAction) {
        when (action) {
            is ClaimInfoAction.SubmitForm -> {
                updateForm(
                    house = action.house,
                    street = action.street,
                    city = action.city,
                    state = action.state,
                    postalCode = action.postalCode
                )
                validateForm()
            }
            is ClaimInfoAction.UpdateLocation -> {
                updateLocation(
                    house = action.house,
                    street = action.street,
                    city = action.city,
                    state = action.state,
                    postalCode = action.postalCode
                )
            }
        }
    }

    fun showLocationError() {
        intent {
            postSideEffect(ClaimInfoSideEffect.ShowLocationErrorToast)
        }
    }

    private fun updateLocation(
        house: String,
        street: String,
        city: String,
        state: String,
        postalCode: String
    ) {
        intent {
            vmState = vmState.copy(
                house = house,
                street = street,
                city = city,
                state = state,
                postalCode = postalCode
            )
            reduce {
                mapper.stateToViewState(vmState)
            }
        }
    }

    private fun validateForm() {
        intent {
            if (isFormValid()) {
                postSideEffect(ClaimInfoSideEffect.NavigateNext)
            } else {
                postSideEffect(ClaimInfoSideEffect.ShowIncompleteFormToast)
            }
        }
    }

    private fun isFormValid(): Boolean {
        val requiredFields = listOf(
            vmState.house,
            vmState.street,
            vmState.city,
            vmState.state,
            vmState.postalCode
        )
        return requiredFields.all { it.isNotBlank() }
    }

    private fun updateForm(
        house: String,
        street: String,
        city: String,
        state: String,
        postalCode: String
    ) {
        vmState = vmState.copy(
            house = house,
            street = street,
            city = city,
            state = state,
            postalCode = postalCode
        )
    }
}