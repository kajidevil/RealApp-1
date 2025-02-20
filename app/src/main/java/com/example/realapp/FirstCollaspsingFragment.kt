package com.example.realapp

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.realapp.databinding.FirstCollapseBinding

class FirstCollapsingFragment : Fragment() {
    //проверка
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
            if (binding.enterNameET.text.toString().trim().isEmpty() ||
                binding.enterEmailET.text.toString().trim().isEmpty() ||
                binding.enterPhoneNumberET.text.toString().trim().isEmpty() ||
                binding.enterFirstNameET.text.toString().trim().isEmpty() ||
                binding.enterLastNameET.text.toString().trim().isEmpty() ||
                binding.enterNotesET.text.toString().trim().isEmpty() ||
                !binding.checkbox.isChecked
            ) {
                showCustomToast()
            } else {
                // Действие при заполненных полях
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
