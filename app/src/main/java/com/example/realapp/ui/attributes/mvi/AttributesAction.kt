package com.example.realapp.ui.attributes.mvi

sealed class AttributesAction {
    class SubmitForm(
        val name: String,
        val email: String,
        val phoneNumber: String,
        val firstName: String,
        val lastName: String,
        val carrierName: String,
        val guidelines: String,
        val isBusiness: Boolean
    ) : AttributesAction()
}

