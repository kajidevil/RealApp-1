package com.example.realapp.ui.attributes

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.realapp.R
import com.example.realapp.databinding.AttributesBinding
import com.example.realapp.ui.attributes.AttributesState
import com.example.realapp.ui.attributes.AttributesIntent
import com.example.realapp.ui.attributes.AttributesViewModel
import com.example.realapp.estimate.domain.model.AttributesData
import com.example.realapp.estimate.domain.model.ClaimInfoData
import com.example.realapp.estimate.domain.model.ProjectManagerData
import com.example.realapp.estimate.domain.model.CustomerData
import com.example.realapp.estimate.domain.model.CarrierData
import com.example.realapp.estimate.domain.model.AddressData
import com.example.realapp.ui.attributes.AttributesSideEffect
import kotlinx.coroutines.launch

class AttributesFragment : Fragment() {

    private var _binding: AttributesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AttributesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AttributesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[AttributesViewModel::class.java]

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        with(binding) {
            enterNameET.doAfterTextChanged { text ->
                viewModel.onIntent(AttributesIntent.UpdateName(text.toString()))
            }
            enterEmailET.doAfterTextChanged { text ->
                viewModel.onIntent(AttributesIntent.UpdateEmail(text.toString()))
            }
            enterPhoneNumberET.doAfterTextChanged { text ->
                viewModel.onIntent(AttributesIntent.UpdatePhoneNumber(text.toString()))
            }
            enterFirstNameET.doAfterTextChanged { text ->
                viewModel.onIntent(AttributesIntent.UpdateFirstName(text.toString()))
            }
            enterLastNameET.doAfterTextChanged { text ->
                viewModel.onIntent(AttributesIntent.UpdateLastName(text.toString()))
            }
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                viewModel.onIntent(AttributesIntent.UpdateCustomerType(isChecked))
            }
            enterNotesET.doAfterTextChanged { text ->
                viewModel.onIntent(AttributesIntent.UpdateCarrierName(text.toString()))
            }
            applyGuideET.doAfterTextChanged { text ->
                viewModel.onIntent(AttributesIntent.UpdateGuidelines(text.toString()))
            }

            nextButton.setOnClickListener {
                viewModel.onIntent(AttributesIntent.SubmitForm)
            }

            // Разворачиваемые блоки
            projectManagerExpandableBtn.setOnClickListener {
                expandableContent1.toggleVisibility()
            }
            customerExpandableBtn.setOnClickListener {
                expandableContent2.toggleVisibility()
            }
            carrierExpandableBtn.setOnClickListener {
                expandableContent3.toggleVisibility()
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.container.sideEffectFlow.collect { effect ->
                    when (effect) {
                        is AttributesSideEffect.ShowIncompleteFormToast ->
                            showCustomToast("Заполните все обязательные поля!")

                        is AttributesSideEffect.NavigateNext ->
                            findNavController().navigate(R.id.action_firstCollapsingFragment_to_secondAttributesFragment)
                    }
                }
            }
        }
    }


    fun View.toggleVisibility() {
        visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
    }

    private fun showCustomToast(message: String) {
        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.custom_toast, null)
        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(8, 0, 8, 0)
        layout.layoutParams = params

        val toast = Toast(requireContext()).apply {
            duration = Toast.LENGTH_LONG
            view = layout
            setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 100)
        }
        toast.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


