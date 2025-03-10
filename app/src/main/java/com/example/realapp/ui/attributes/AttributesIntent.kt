package com.example.realapp.ui.attributes

sealed class AttributesIntent {
    data class UpdateForm(
        val name: String,
        val email: String,
        val phone: String,
        val firstName: String,
        val lastName: String,
        val notes: String,
        val isBusiness: Boolean
    ) : AttributesIntent()

    object SubmitForm : AttributesIntent()
    object ExpandProjectManager : AttributesIntent()
    object ExpandCustomer : AttributesIntent()
    object ExpandCarrier : AttributesIntent()
}
