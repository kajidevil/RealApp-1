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
import com.example.realapp.databinding.SecondAttributesBinding
import com.example.realapp.estimate.domain.model.AddressData
import com.example.realapp.estimate.domain.model.ClaimInfoData

class SecondAttributesFragment : Fragment() {

    private var _binding: SecondAttributesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AttributesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SecondAttributesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(AttributesViewModel::class.java)

        binding.nextButton2.setOnClickListener {
            if (isFormValid()) {
                saveDataToViewModel() // Сохраняем данные перед переходом

                val thirdFragment = ThirdFragment()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, thirdFragment)
                    .addToBackStack(null)
                    .commit()
            } else {
                showCustomToast()
            }
        }

        binding.addressExpandableBtn.setOnClickListener {
            toggleVisibility(binding.expandableContent4, binding.addressExpandableBtn)
        }
    }

    private fun isFormValid(): Boolean {
        return binding.enterHouseET.text.toString().trim().isNotEmpty() &&
                binding.enterStreetET.text.toString().trim().isNotEmpty() &&
                binding.enterStateET.text.toString().trim().isNotEmpty() &&
                binding.enterCityET.text.toString().trim().isNotEmpty() &&
                binding.enterPostalCodeET.text.toString().trim().isNotEmpty()
    }

    private fun saveDataToViewModel() {
        val addressData = AddressData(
            houseName = binding.enterHouseET.text.toString(),
            streetName = binding.enterStreetET.text.toString(),
            cityName = binding.enterCityET.text.toString(),
            stateName = binding.enterStateET.text.toString(),
            postalCodeName = binding.enterPostalCodeET.text.toString()
        )

        val claimInfoData = ClaimInfoData(adress = addressData)

        viewModel.claimInfoData.value = claimInfoData // Сохраняем данные в ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
}
