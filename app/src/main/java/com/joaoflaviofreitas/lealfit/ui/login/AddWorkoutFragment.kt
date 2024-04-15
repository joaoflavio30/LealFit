package com.joaoflaviofreitas.lealfit.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.joaoflaviofreitas.lealfit.databinding.FragmentAddWorkoutBinding
import com.joaoflaviofreitas.lealfit.ui.login.domain.model.Workout
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@AndroidEntryPoint
class AddWorkoutFragment : Fragment() {

    private val viewModel: AddWorkoutViewModel by viewModels()

    private var _binding: FragmentAddWorkoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.createBtn.setOnClickListener {
            if (checkFields()) createWorkout()

        }
        binding.dateBtn.setOnClickListener {
            openCalendar()
        }
    }

    private fun openCalendar() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
        datePicker
            .addOnPositiveButtonClickListener { selectedDateInMillis ->
                val selectedDate = Date(selectedDateInMillis)
                val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
                dateFormat.timeZone = TimeZone.getTimeZone("UTC")
                val formattedDate = dateFormat.format(selectedDate)

                binding.textDate.text = formattedDate
            }

        datePicker.show(parentFragmentManager, "DatePicker")
    }

    private fun createWorkout() {
        val workout = Workout(
            name = binding.inputName.text.toString(),
            date = binding.textDate.text.toString(),
            description = binding.inputDescription.text.toString(),
        )
        viewModel.createWorkout(workout)
    }

    private fun checkFields(): Boolean {
        val name = binding.inputName.text.toString()
        val date = binding.textDate.text.toString()
        var valid = false
        if (name.isEmpty()) {
            binding.inputName.setError("Enter a name.", null)
            binding.inputName.requestFocus()
        }
        if (date.isEmpty()) {
            showToastLengthLong("Please put the Workout Date")
        } else valid = true

        return valid
    }

    private fun showToastLengthLong(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG)
            .show()
    }
}