package com.example.realapp.ui.attributes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realapp.ui.attributes.mapper.AttributesMapper
import com.example.realapp.ui.attributes.mvi.AttributesAction
import com.example.realapp.ui.attributes.mvi.AttributesSideEffect
import com.example.realapp.ui.attributes.mvi.AttributesState
import com.example.realapp.ui.attributes.mvi.AttributesViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import javax.inject.Inject

//private val mapper:AttributesMapper,?
@HiltViewModel
class AttributesViewModel @Inject constructor(
    private val mapper: AttributesMapper
) : ViewModel(), ContainerHost<AttributesViewState, AttributesSideEffect> {

    private var vmState: AttributesState = AttributesState()

    override val container =
        viewModelScope.container<AttributesViewState, AttributesSideEffect>(
            mapper.stateToViewState(vmState)
        )

    fun action(action: AttributesAction) {
        when (action) {
            is AttributesAction.SubmitForm -> {
                updateFormData(
                    name = action.name,
                    email = action.email,
                    phoneNumber = action.phoneNumber,
                    firstName = action.firstName,
                    lastName = action.lastName,
                    carrierName = action.carrierName,
                    guidelines = action.guidelines,
                    isBusiness = action.isBusiness
                )
                validateForm()
            }
        }

    }

    private fun validateForm() {
        intent {
            if (isFormValid()) {
                postSideEffect(AttributesSideEffect.NavigateNext)
            } else {
                postSideEffect(AttributesSideEffect.ShowIncompleteFormToast)
            }
        }
    }

    private fun updateFormData(
        name: String,
        email: String,
        phoneNumber: String,
        firstName: String,
        lastName: String,
        carrierName: String,
        guidelines: String,
        isBusiness: Boolean
    ) {
        vmState = vmState.copy(
            name = name,
            email = email,
            phoneNumber = phoneNumber,
            firstName = firstName,
            lastName = lastName,
            carrierName = carrierName,
            guidelines = guidelines,
            customerIsBusiness = isBusiness
        )
    }

    private fun isFormValid(): Boolean {
        val requiredFields = listOf(
            vmState.name,
            vmState.email,
            vmState.phoneNumber,
            vmState.firstName,
            vmState.lastName,
            vmState.carrierName
        )
        return requiredFields.all { it.isNotBlank() }
    }

}


