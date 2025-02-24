package com.example.realapp.ui.attributes

import android.Manifest

import android.animation.ObjectAnimator
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.realapp.R
import com.example.realapp.databinding.SecondAttributesBinding
import com.example.realapp.estimate.domain.model.AddressData
import com.example.realapp.estimate.domain.model.ClaimInfoData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class SecondAttributesFragment : Fragment() {

    private var _binding: SecondAttributesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AttributesViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient

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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        binding.nextButton2.setOnClickListener {
            if (isFormValid()) {
                saveDataToViewModel()

                val thirdFragment = ThirdFragment()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, thirdFragment)
                    .addToBackStack(null)
                    .commit()
            } else {
                showCustomToast()
            }
        }

        binding.btnGetLocation.setOnClickListener {
            checkLocationPermission()
        }

        binding.addressExpandableBtn.setOnClickListener {
            toggleVisibility(binding.expandableContent4, binding.addressExpandableBtn)
        }
        binding.lossTypesExpandableBtn.setOnClickListener {
            toggleVisibility(binding.expandableContent5, binding.lossTypesExpandableBtn)
        }
        binding.waterLossBtn.setOnClickListener {
            binding.waterLossBtn.isSelected = !binding.waterLossBtn.isSelected

            // Переключение видимости контента
            binding.expandableContent6.visibility =
                if (binding.waterLossBtn.isSelected) View.VISIBLE else View.GONE
        }

        val clickableViews = listOf(
            binding.cat1Btn,
            binding.cat2Btn,
            binding.cat3Btn,
            binding.classNotDefinedBtnOne
        )
        val clickableViews2 = listOf(
            binding.class1Btn,
            binding.class2Btn,
            binding.class3Btn,
            binding.class4Btn,
            binding.classNotDefinedBtnTwo
        )
        val clickableViews3 = listOf(
            binding.fireLossBtn,
            binding.vehicleLossBtn,
            binding.traumaBtn,
            binding.environmentalLossBtn,
            binding.otherLossBtn
        )

        clickableViews.forEach { view ->
            view.setOnClickListener {
                setSelectedState(view, clickableViews)
            }
        }
        clickableViews2.forEach { view ->
            view.setOnClickListener {
                setSelectedState(view, clickableViews2)
            }
        }
        // Для третьего списка: множественный выбор
        clickableViews3.forEach { view ->
            view.setOnClickListener {
                view.isSelected = !view.isSelected
            }
        }

    }
    private fun setSelectedState(selectedView: View, allViews: List<View>) {
        allViews.forEach { view ->
            view.isSelected = view == selectedView
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
        viewModel.claimInfoData.value = claimInfoData
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            getLocation()
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getLocation()
            } else {
                Toast.makeText(requireContext(), "Разрешение не предоставлено", Toast.LENGTH_SHORT).show()
            }
        }

    private fun getLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    Toast.makeText(requireContext(), "Широта: $latitude, Долгота: $longitude", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Локация недоступна", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun toggleVisibility(content: View, arrowButton: ImageButton) {
        val isVisible = content.visibility == View.VISIBLE

        if (isVisible) {
            val animator = ObjectAnimator.ofFloat(content, "alpha", 1f, 0f)
            animator.duration = 300
            animator.start()
            content.visibility = View.GONE
            arrowButton.setImageResource(R.drawable.ic_arrow_down)
        } else {
            content.visibility = View.VISIBLE
            val animator = ObjectAnimator.ofFloat(content, "alpha", 0f, 1f)
            animator.duration = 300
            animator.start()
            arrowButton.setImageResource(R.drawable.ic_arrow_up)
        }
    }

    private fun showCustomToast() {
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
}
