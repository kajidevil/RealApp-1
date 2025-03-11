package com.example.realapp.ui.attributes

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container



class AttributesViewModel : ViewModel(), ContainerHost<AttributesState, AttributesSideEffect> {

    override val container = container<AttributesState, AttributesSideEffect>(AttributesState())

    fun onIntent(intent: AttributesIntent) {
        when (intent) {
            is AttributesIntent.UpdateName -> updateState { it.copy(name = intent.name) }
            is AttributesIntent.UpdateEmail -> updateState { it.copy(email = intent.email) }
            is AttributesIntent.UpdatePhoneNumber -> updateState { it.copy(phoneNumber = intent.phoneNumber) }
            is AttributesIntent.UpdateFirstName -> updateState { it.copy(firstName = intent.firstName) }
            is AttributesIntent.UpdateLastName -> updateState { it.copy(lastName = intent.lastName) }
            is AttributesIntent.UpdateCustomerType -> updateState { it.copy(customerIsBusiness = intent.isBusiness) }
            is AttributesIntent.UpdateCarrierName -> updateState { it.copy(carrierName = intent.carrierName) }
            is AttributesIntent.UpdateGuidelines -> updateState { it.copy(guidelines = intent.guidelines) }
            is AttributesIntent.SubmitForm -> validateForm()
        }
    }

    private fun updateState(update: (AttributesState) -> AttributesState) {
        intent { reduce { update(state) } }
    }

    private fun validateForm() {
        intent {
            if (isFormValid(state)) {
                postSideEffect(AttributesSideEffect.NavigateNext)
            } else {
                postSideEffect(AttributesSideEffect.ShowIncompleteFormToast)
            }
        }
    }

    private fun isFormValid(state: AttributesState): Boolean {
        val requiredFields = listOf(
            state.name,
            state.email,
            state.phoneNumber,
            state.firstName,
            state.lastName,
            state.carrierName
        )
        return requiredFields.all { it.isNotBlank() }
    }

}


