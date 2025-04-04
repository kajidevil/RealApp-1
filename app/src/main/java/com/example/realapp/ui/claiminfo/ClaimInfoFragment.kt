package com.example.realapp.ui.claiminfo

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.realapp.R
import com.example.realapp.databinding.ClaimInfoBinding
import com.example.realapp.databinding.CustomToastBinding
import com.example.realapp.ui.claiminfo.mvi.ClaimInfoAction
import com.example.realapp.ui.claiminfo.mvi.ClaimInfoSideEffect
import com.example.realapp.ui.claiminfo.mvi.ClaimInfoViewState
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe
import java.util.Locale

@AndroidEntryPoint
class ClaimInfoFragment : Fragment() {

    private var _binding: ClaimInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ClaimInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ClaimInfoBinding.inflate(inflater, container, false)
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

    private fun handleState(state: ClaimInfoViewState) = with(binding) {
        enterHouseET.setText(state.house)
        enterStreetET.setText(state.street)
        enterCityET.setText(state.city)
        enterStateET.setText(state.state)
        enterPostalCodeET.setText(state.postalCode)
    }

    private fun handleSideEffect(effect: ClaimInfoSideEffect) {
        when (effect) {
            is ClaimInfoSideEffect.NavigateNext ->
                findNavController().navigate(R.id.action_claimInfoFragment_to_estimateFragment)

            ClaimInfoSideEffect.ShowIncompleteFormToast ->
                showCustomToast2(getString(R.string.toasttext))

            ClaimInfoSideEffect.ShowLocationErrorToast ->
                showCustomToast2(getString(R.string.toasttext2))
        }
    }

    private fun setupListeners() {
        with(binding) {

            nextButton2.setOnClickListener {
                viewModel.action(
                    ClaimInfoAction.SubmitForm(
                        house = enterHouseET.text.toString(),
                        street = enterStreetET.text.toString(),
                        city = enterCityET.text.toString(),
                        state = enterStateET.text.toString(),
                        postalCode = enterPostalCodeET.text.toString()
                    )
                )
            }

            btnGetLocation.setOnClickListener {
                getLocation()
            }

            addressExpandableBtn.setOnClickListener {
                expandableContent4.toggleVisibility()
            }
            lossTypesExpandableBtn.setOnClickListener {
                expandableContent5.toggleVisibility()
            }
            waterLossBtn.setOnClickListener {
                expandableContent6.toggleVisibility()
            }
        }
    }

    private fun View.toggleVisibility() {
        visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
    }

    private fun showCustomToast2(message: String) {
        val inflater = LayoutInflater.from(requireContext())
        val binding = CustomToastBinding.inflate(inflater) // CustomToastBinding - это автогенерированный класс от custom_toast.xml

        binding.tvToastMessage.text = message // Устанавливаем текст в TextView

        val toast = Toast(requireContext()).apply {
            duration = Toast.LENGTH_LONG
            view = binding.root
            setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 100)
        }
        toast.show()
    }

    private fun getLocation() {
        if (!checkLocationPermission()) {
            requestLocationPermission()
            return
        }

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    getAddressFromLocation(location.latitude, location.longitude)
                } else {
                    viewModel.showLocationError()
                }
            }
            .addOnFailureListener {
                viewModel.showLocationError()
            }
    }

    private fun getAddressFromLocation(latitude: Double, longitude: Double) {
        try {
            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)

            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]
                val house = address.subThoroughfare ?: ""  // Номер дома
                val street = address.thoroughfare ?: ""   // Улица
                val city = address.locality ?: ""         // Город
                val state = address.adminArea ?: ""       // Регион/штат
                val postalCode = address.postalCode ?: "" // Почтовый индекс

                viewModel.action(
                    ClaimInfoAction.UpdateLocation(
                        house,
                        street,
                        city,
                        state,
                        postalCode
                    )
                )
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Ошибка определения адреса", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION_PERMISSION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Разрешение на геолокацию не выдано",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1001
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
