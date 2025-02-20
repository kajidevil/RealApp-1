package com.example.realapp.ui.attributes

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.realapp.R
import com.example.realapp.databinding.FirstCollapseBinding
import com.example.realapp.estimate.domain.model.AttributesData
import com.example.realapp.estimate.domain.model.CarrierData
import com.example.realapp.estimate.domain.model.CustomerData
import com.example.realapp.estimate.domain.model.ProjectManagerData

class FirstCollapsingFragment : Fragment() {

    private var _binding: FirstCollapseBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AttributesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FirstCollapseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(AttributesViewModel::class.java)

        binding.nextButton.setOnClickListener {
            if (isFormValid()) {
                saveDataToViewModel() // Сохраняем данные перед переходом

                val secondFragment = SecondAttributesFragment()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, secondFragment)
                    .addToBackStack(null)
                    .commit()
            } else {
                showCustomToast()
            }
        }

        binding.projectManagerExpandableBtn.setOnClickListener {
            toggleVisibility(binding.expandableContent1, binding.projectManagerExpandableBtn)
        }
        binding.customerExpandableBtn.setOnClickListener {
            toggleVisibility(binding.expandableContent2, binding.customerExpandableBtn)
        }
        binding.carrierExpandableBtn.setOnClickListener {
            toggleVisibility(binding.expandableContent3, binding.carrierExpandableBtn)
        }
    }

    private fun isFormValid(): Boolean {
        return binding.enterNameET.text.toString().trim().isNotEmpty() &&
                binding.enterEmailET.text.toString().trim().isNotEmpty() &&
                binding.enterPhoneNumberET.text.toString().trim().isNotEmpty() &&
                binding.enterFirstNameET.text.toString().trim().isNotEmpty() &&
                binding.enterLastNameET.text.toString().trim().isNotEmpty() &&
                binding.enterNotesET.text.toString().trim().isNotEmpty() &&
                binding.checkbox.isChecked
    }

    private fun saveDataToViewModel() {
        val projectManagerData = ProjectManagerData(
            firstName = binding.enterFirstNameET.text.toString(),
            email = binding.enterEmailET.text.toString(),
            lastName = binding.enterLastNameET.text.toString(),
            phoneNumber = binding.enterPhoneNumberET.text.toString()
        )

        val customerData = CustomerData(
            firstName = binding.enterNameET.text.toString(),
            lastName = binding.enterLastNameET.text.toString(),
            customerIsBusiness = binding.checkbox.isChecked
        )

        val carrierData = CarrierData(
            carrierName = binding.enterNotesET.text.toString(),
            carrierGuideLines = null
        )

        val attributesData = AttributesData(
            projectManager = projectManagerData,
            customerData = customerData,
            carrierData = carrierData
        )

        viewModel.setAttributesData(attributesData)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun showCustomToast() {
        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.custom_toast, null)

        // Устанавливаем отступы программно
        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, // Растягиваем на всю ширину
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(8, 0, 8, 0) // Отступы слева и справа
        layout.layoutParams = params

        val toast = Toast(requireContext()).apply {
            duration = Toast.LENGTH_LONG // Увеличиваем время отображения
            view = layout
            setGravity(
                Gravity.BOTTOM or Gravity.FILL_HORIZONTAL,
                0,
                100
            ) // Заполняем всю ширину и опускаем вниз
        }
        toast.show()
    }
    private fun toggleVisibility(content: View, arrowButton: ImageButton) {
        val isVisible = content.visibility == View.VISIBLE

        if (isVisible) {
            // Скрываем с анимацией
            val animator = ObjectAnimator.ofFloat(content, "alpha", 1f, 0f)
            animator.duration = 300
            animator.start()
            content.visibility = View.GONE
            arrowButton.setImageResource(R.drawable.ic_arrow_down)  // Для ImageButton
        } else {
            // Показываем с анимацией
            content.visibility = View.VISIBLE
            val animator = ObjectAnimator.ofFloat(content, "alpha", 0f, 1f)
            animator.duration = 300
            animator.start()
            arrowButton.setImageResource(R.drawable.ic_arrow_up)  // Для ImageButton
        }
    }
}

