package com.example.realapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.realapp.databinding.FirstCollapseBinding
import com.example.realapp.databinding.FirstFragmentBinding

class FirstCollapsingFragment : Fragment() {

    private var _binding: FirstCollapseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FirstCollapseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextButton.setOnClickListener {
            if (binding.editText11.text.toString().trim().isEmpty() ||
                binding.editText12.text.toString().trim().isEmpty() ||
                binding.editText13.text.toString().trim().isEmpty() ||
                binding.editText21.text.toString().trim().isEmpty() ||
                binding.editText22.text.toString().trim().isEmpty() ||
                binding.editText41.text.toString().trim().isEmpty() ||
                !binding.checkbox.isChecked
            ) {
                showCustomToast()
            } else {
                // Действие при заполненных полях
            }
        }

        binding.arrowButton1.setOnClickListener {
            toggleVisibility()
        }
        binding.arrowButton2.setOnClickListener {
            toggleVisibility2()
        }
        binding.arrowButton3.setOnClickListener {
            toggleVisibility3()
        }
    }

    private fun toggleVisibility() {
        val isVisible = binding.expandableContent1.visibility == View.VISIBLE
        binding.expandableContent1.visibility = if (isVisible) View.GONE else View.VISIBLE
        binding.arrowButton1.setImageResource(
            if (isVisible) R.drawable.ic_arrow_down else R.drawable.ic_arrow_up
        )
    }
    private fun toggleVisibility2() {
        val isVisible = binding.expandableContent2.visibility == View.VISIBLE
        binding.expandableContent2.visibility = if (isVisible) View.GONE else View.VISIBLE
        binding.arrowButton2.setImageResource(
            if (isVisible) R.drawable.ic_arrow_down else R.drawable.ic_arrow_up
        )
    }
    private fun toggleVisibility3() {
        val isVisible = binding.expandableContent3.visibility == View.VISIBLE
        binding.expandableContent3.visibility = if (isVisible) View.GONE else View.VISIBLE
        binding.arrowButton3.setImageResource(
            if (isVisible) R.drawable.ic_arrow_down else R.drawable.ic_arrow_up
        )
    }

    private fun showCustomToast() {
        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.custom_toast, null)

        val toast = Toast(requireContext()).apply {
            duration = Toast.LENGTH_SHORT
            view = layout
        }
        toast.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
