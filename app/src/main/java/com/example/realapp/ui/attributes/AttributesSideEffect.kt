package com.example.realapp.ui.attributes

sealed class AttributesSideEffect {
    object NavigateNext : AttributesSideEffect()
    object ShowIncompleteFormToast : AttributesSideEffect()
}

