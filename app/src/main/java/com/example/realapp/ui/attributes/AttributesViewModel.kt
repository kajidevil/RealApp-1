package com.example.realapp.ui.attributes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.realapp.estimate.domain.model.AttributesData
import com.example.realapp.estimate.domain.model.ClaimInfoData
import com.example.realapp.estimate.domain.model.ProjectManagerData
import com.example.realapp.estimate.domain.model.CustomerData
import com.example.realapp.estimate.domain.model.CarrierData


class AttributesViewModel : ViewModel() {

    private var isProjectManagerExpanded = false
    private var isCustomerExpanded = false
    private var isCarrierExpanded = false

    private val _state = MutableLiveData<AttributesState>()
    val state: LiveData<AttributesState> get() = _state

    private val _attributesData = MutableLiveData<AttributesData>()
    val attributesData: LiveData<AttributesData> get() = _attributesData

    private val _claimInfoData = MutableLiveData<ClaimInfoData>()
    val claimInfoData: LiveData<ClaimInfoData> get() = _claimInfoData

    fun processIntent(intent: AttributesIntent) {
        when (intent) {
            is AttributesIntent.UpdateForm -> {
                // Обновляем LiveData с данными формы
                _attributesData.value = AttributesData(
                    projectManager = ProjectManagerData(
                        firstName = intent.firstName,
                        lastName = intent.lastName,
                        email = intent.email,
                        phoneNumber = intent.phone
                    ),
                    customerData = CustomerData(
                        firstName = intent.name,
                        lastName = intent.lastName,
                        customerIsBusiness = intent.isBusiness
                    ),
                    carrierData = CarrierData(
                        carrierName = "Carrier name", // Можешь добавить поле для ввода
                        carrierGuideLines = intent.notes
                    )
                )
            }

            AttributesIntent.SubmitForm -> {
                if (isFormValid()) {
                    _state.value = AttributesState.Success
                } else {
                    _state.value = AttributesState.Error("Форма заполнена некорректно!")
                }
            }

            AttributesIntent.ExpandProjectManager -> {
                isProjectManagerExpanded = !isProjectManagerExpanded
                _state.value = AttributesState.ExpandProjectManager(isProjectManagerExpanded)
            }

            AttributesIntent.ExpandCustomer -> {
                isCustomerExpanded = !isCustomerExpanded
                _state.value = AttributesState.ExpandCustomer(isCustomerExpanded)
            }

            AttributesIntent.ExpandCarrier -> {
                isCarrierExpanded = !isCarrierExpanded
                _state.value = AttributesState.ExpandCarrier(isCarrierExpanded)
            }

            else -> {}
        }
    }


    private fun isFormValid(): Boolean {
        val attributes = _attributesData.value
        return attributes != null &&
                attributes.projectManager != null &&
                attributes.customerData != null &&
                attributes.carrierData != null &&
                attributes.projectManager.firstName.isNotEmpty() &&
                attributes.customerData.firstName.isNotEmpty() &&
                attributes.carrierData.carrierName.isNotEmpty()
    }

}
