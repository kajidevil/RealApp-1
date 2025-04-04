package com.example.realapp.ui.claiminfo.mvi

sealed class ClaimInfoSideEffect {
    object ShowIncompleteFormToast : ClaimInfoSideEffect()
    object NavigateNext : ClaimInfoSideEffect()
   object ShowLocationErrorToast : ClaimInfoSideEffect()
}