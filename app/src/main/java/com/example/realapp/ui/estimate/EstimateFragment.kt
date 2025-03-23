package com.example.realapp.ui.estimate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.realapp.databinding.ClaimInfoBinding
import com.example.realapp.databinding.Estimate1Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EstimateFragment : Fragment(){

    private var _binding: Estimate1Binding?=null
    private val binding get() = _binding!!
    private val viewModel: EstimateViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = Estimate1Binding.inflate(inflater, container, false)
        return binding.root
    }
}