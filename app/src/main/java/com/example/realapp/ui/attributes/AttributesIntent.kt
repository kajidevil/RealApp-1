package com.example.realapp.ui.attributes

sealed class AttributesIntent {
    data class UpdateName(val name: String) : AttributesIntent()
    data class UpdateEmail(val email: String) : AttributesIntent()
    data class UpdatePhoneNumber(val phoneNumber: String) : AttributesIntent()
    data class UpdateFirstName(val firstName: String) : AttributesIntent()
    data class UpdateLastName(val lastName: String) : AttributesIntent()
    data class UpdateCustomerType(val isBusiness: Boolean) : AttributesIntent()
    data class UpdateCarrierName(val carrierName: String) : AttributesIntent()
    data class UpdateGuidelines(val guidelines: String) : AttributesIntent()
    object SubmitForm : AttributesIntent()
}

