package com.example.realapp.ui.attributes

sealed class AttributesState {
    object Initial : AttributesState()
    object Loading : AttributesState()
    object Success : AttributesState()
    data class Error(val message: String) : AttributesState()

    // Состояние для разворота секции
    data class ExpandProjectManager(val isExpanded: Boolean) : AttributesState()
    data class ExpandCustomer(val isExpanded: Boolean) : AttributesState()
    data class ExpandCarrier(val isExpanded: Boolean) : AttributesState()
}

