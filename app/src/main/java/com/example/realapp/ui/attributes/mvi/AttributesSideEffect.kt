package com.example.realapp.ui.attributes.mvi

sealed class AttributesSideEffect {
    object NavigateNext : AttributesSideEffect()
    object ShowIncompleteFormToast : AttributesSideEffect()
}

