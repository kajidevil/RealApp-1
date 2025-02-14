package com.example.realapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private lateinit var nextButton: Button
    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPhoneNumber: EditText
    private lateinit var editTextFirstName: EditText
    private lateinit var editTextLastName: EditText
    private lateinit var editTextCarrierName: EditText
    private lateinit var editcheckbox: CheckBox

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.first_fragment, container, false)

        // Инициализация элементов после создания view
        nextButton = view.findViewById(R.id.nextButton)
        editTextName = view.findViewById(R.id.editText1_1)
        editTextEmail = view.findViewById(R.id.editText1_2)
        editTextPhoneNumber = view.findViewById(R.id.editText1_3)
        editTextFirstName = view.findViewById(R.id.editText2_1)
        editTextLastName = view.findViewById(R.id.editText2_2)
        editTextCarrierName = view.findViewById(R.id.editText4_1)
        editcheckbox = view.findViewById(R.id.checkbox)

        nextButton.setOnClickListener {
            // Проверяем, что все поля не пустые и чекбокс выбран
            if (editTextName.text.toString().trim().isEmpty() ||
                editTextEmail.text.toString().trim().isEmpty() ||
                editTextPhoneNumber.text.toString().trim().isEmpty() ||
                editTextFirstName.text.toString().trim().isEmpty() ||
                editTextLastName.text.toString().trim().isEmpty() ||
                editTextCarrierName.text.toString().trim().isEmpty() ||
                !editcheckbox.isChecked // Проверяем, что чекбокс выбран
            ) {
                // Если хотя бы одно поле пустое или чекбокс не выбран, показываем кастомный Toast
                showCustomToast()
            } else {
                // Действия, если все поля заполнены и чекбокс выбран
            }
        }

        return view
    }

    private fun showCustomToast() {
        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.custom_toast, null)

        // Создаём Toast
        val toast = Toast(requireContext()).apply {
            duration = Toast.LENGTH_SHORT
            view = layout
        }

        toast.show()
    }
}
