package com.example.myapplication.ui.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.databinding.BmiFragmentBinding
import com.example.myapplication.data.entities.BMIResponse
import com.example.myapplication.ui.viewmodel.BMIViewModel
import com.example.myapplication.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BMIFragment : Fragment() {
    private var binding: BmiFragmentBinding by autoCleared()
    private val bmiViewModel: BMIViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BmiFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeBMIPickerValues()
        observeBMI()

        binding.btnCalculateBMI.setOnClickListener {
            val height = binding.heightNumberPicker.value
            val weight = binding.WeightNumberPicker.value
            calculateBmi(height, weight)
        }
    }

    private fun initializeBMIPickerValues() {
        val heightPicker = binding.heightNumberPicker
        val weightPicker = binding.WeightNumberPicker

        heightPicker.minValue = 100
        heightPicker.maxValue = 230

        weightPicker.minValue = 1
        weightPicker.maxValue = 180
    }

    private fun observeBMI() {
        bmiViewModel.bmi.observe(viewLifecycleOwner) { bmi ->
            bmi?.let {
                showResultDialog(bmi)
            }
        }

        bmiViewModel.errorMessage.observe(viewLifecycleOwner) { error: String? ->
            error.let { Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show() }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showResultDialog(bmi: BMIResponse) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_bmi_result)

        val bmiValueResult = dialog.findViewById<TextView>(R.id.bmiValueResult)
        val bmiClassResult = dialog.findViewById<TextView>(R.id.bmiClassResult)

        val btnClose = dialog.findViewById<Button>(R.id.btnClose)

        println(bmi)
        bmiValueResult.text = "BMI : " + bmi.bmi.toString()
        bmiClassResult.text = "CLASS : " + bmi.bmiClass

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun calculateBmi(height: Int, weight: Int) {
        bmiViewModel.getBMI(height, weight, "metric")
    }
}
