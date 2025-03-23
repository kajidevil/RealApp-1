package com.example.realapp.ui.attributes

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.realapp.R
import com.example.realapp.databinding.AttributesBinding
import com.example.realapp.ui.attributes.mvi.AttributesAction
import com.example.realapp.ui.attributes.mvi.AttributesSideEffect
import com.example.realapp.ui.attributes.mvi.AttributesViewState
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe

@AndroidEntryPoint
class AttributesFragment : Fragment() {

    private var _binding: AttributesBinding? = null
    private val binding get() = _binding!!

    // Теперь ViewModel создаётся через Hilt
    private val viewModel: AttributesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AttributesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observe(
            lifecycleOwner = viewLifecycleOwner,
            state = ::handleState,
            sideEffect = ::handleSideEffect
        )
        setupListeners()
    }

    private fun handleState(state: AttributesViewState) = with(binding) {
        enterNameET.setText(state.name)
        enterEmailET.setText(state.email)
        enterPhoneNumberET.setText(state.phoneNumber)
        enterFirstNameET.setText(state.firstName)
        enterLastNameET.setText(state.lastName)
        customerIsBusinessCheckbox.isChecked = state.customerIsBusiness
        enterCarrierNameET.setText(state.carrierName)
        enterGuidelinesET.setText(state.guidelines)
    }

    private fun handleSideEffect(effect: AttributesSideEffect) {
        when (effect) {
            is
            AttributesSideEffect.NavigateNext ->
                findNavController().navigate(R.id.action_attributesFragment_to_claimInfoFragment)

            AttributesSideEffect.ShowIncompleteFormToast ->
                showCustomToast("Заполните все обязательные поля!")
        }
    }

    private fun setupListeners() {
        with(binding) {

            nextButton.setOnClickListener {
                viewModel.action(
                    AttributesAction.SubmitForm(
                        name = enterNameET.text.toString(),
                        email = enterEmailET.text.toString(),
                        phoneNumber = enterPhoneNumberET.text.toString(),
                        firstName = enterFirstNameET.text.toString(),
                        lastName = enterLastNameET.text.toString(),
                        carrierName = enterCarrierNameET.text.toString(),
                        guidelines = enterGuidelinesET.text.toString(),
                        isBusiness = customerIsBusinessCheckbox.isChecked
                    )
                )
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

    private fun View.toggleVisibility() {
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


