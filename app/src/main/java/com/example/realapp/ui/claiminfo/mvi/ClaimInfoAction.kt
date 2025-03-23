package com.example.realapp.ui.claiminfo.mvi

sealed class ClaimInfoAction {
    data class SubmitForm(
        val house:String,
        val street:String,
        val city: String,
        val state:String,
        val postalCode:String,
    ):ClaimInfoAction()

    data class UpdateLocation(
        val house: String,
        val street: String,
        val city: String,
        val state: String,
        val postalCode: String
    ) : ClaimInfoAction()
}