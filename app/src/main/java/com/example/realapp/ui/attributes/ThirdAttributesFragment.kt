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
import com.example.realapp.databinding.TemporaryForCheckBinding

class ThirdFragment : Fragment() {
    private lateinit var viewModel: AttributesViewModel
    private var _binding: TemporaryForCheckBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TemporaryForCheckBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(AttributesViewModel::class.java)

        viewModel.attributesData.observe(viewLifecycleOwner) { attributes ->
            viewModel.claimInfoData.observe(viewLifecycleOwner) { claimInfo ->
                binding.tvResult.text = """
                    📌 **Project Manager**
                    Имя: ${attributes.projectManager?.firstName}
                    Email: ${attributes.projectManager?.email}
                    Фамилия: ${attributes.projectManager?.lastName}
                    Телефон: ${attributes.projectManager?.phoneNumber}

                    📌 **Customer**
                    Имя: ${attributes.customerData?.firstName}
                    Фамилия: ${attributes.customerData?.lastName}
                    Бизнес-клиент: ${if (attributes.customerData?.customerIsBusiness == true) "Да" else "Нет"}

                    📌 **Carrier**
                    Название перевозчика: ${attributes.carrierData?.carrierName}
                    Инструкции: ${attributes.carrierData?.carrierGuideLines ?: "Нет данных"}

                    📌 **Claim Info**
                    Дом: ${claimInfo.adress?.houseName}
                    Улица: ${claimInfo.adress?.streetName}
                    Город: ${claimInfo.adress?.cityName}
                    Штат: ${claimInfo.adress?.stateName}
                    Почтовый индекс: ${claimInfo.adress?.postalCodeName}
                """.trimIndent()
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

