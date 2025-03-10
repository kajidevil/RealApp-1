package com.example.realapp.ui.attributes

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

        viewModel = ViewModelProvider(requireActivity())[AttributesViewModel::class.java]

        binding.nextButton.setOnClickListener {
            viewModel.processIntent(AttributesIntent.SubmitForm)
        }

        binding.projectManagerExpandableBtn.setOnClickListener {
            viewModel.processIntent(AttributesIntent.ExpandProjectManager)
        }

        binding.customerExpandableBtn.setOnClickListener {
            viewModel.processIntent(AttributesIntent.ExpandCustomer)
        }

        binding.carrierExpandableBtn.setOnClickListener {
            viewModel.processIntent(AttributesIntent.ExpandCarrier)
        }

        // Наблюдаем за состоянием
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is AttributesState.ExpandProjectManager -> {
                    if (state.isExpanded) {
                        binding.expandableContent1.visibility = View.VISIBLE
                    } else {
                        binding.expandableContent1.visibility = View.GONE
                    }
                }

                is AttributesState.ExpandCustomer -> {
                    if (state.isExpanded) {
                        binding.expandableContent2.visibility = View.VISIBLE
                    } else {
                        binding.expandableContent2.visibility = View.GONE
                    }
                }

                is AttributesState.ExpandCarrier -> {
                    if (state.isExpanded) {
                        binding.expandableContent3.visibility = View.VISIBLE
                    } else {
                        binding.expandableContent3.visibility = View.GONE
                    }
                }


                is AttributesState.Success -> {
                    findNavController().navigate(R.id.action_firstCollapsingFragment_to_secondAttributesFragment)
                }

                is AttributesState.Error -> {
                    showCustomToast(state.message)
                }

                else -> Unit
            }
        }


        // Отслеживаем ввод данных
        setupFormListeners()
    }

    private fun setupFormListeners() {
        binding.enterNameET.addTextChangedListener {
            updateForm()
        }
        binding.enterEmailET.addTextChangedListener {
            updateForm()
        }
        binding.enterPhoneNumberET.addTextChangedListener {
            updateForm()
        }
        binding.enterFirstNameET.addTextChangedListener {
            updateForm()
        }
        binding.enterLastNameET.addTextChangedListener {
            updateForm()
        }
        binding.enterNotesET.addTextChangedListener {
            updateForm()
        }
        binding.checkbox.setOnCheckedChangeListener { _, _ ->
            updateForm()
        }
    }

    private fun updateForm() {
        viewModel.processIntent(
            AttributesIntent.UpdateForm(
                name = binding.enterNameET.text.toString(),
                email = binding.enterEmailET.text.toString(),
                phone = binding.enterPhoneNumberET.text.toString(),
                firstName = binding.enterFirstNameET.text.toString(),
                lastName = binding.enterLastNameET.text.toString(),
                notes = binding.enterNotesET.text.toString(),
                isBusiness = binding.checkbox.isChecked
            )
        )
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


