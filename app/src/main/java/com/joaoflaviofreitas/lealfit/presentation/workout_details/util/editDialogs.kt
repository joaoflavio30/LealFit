package com.joaoflaviofreitas.lealfit.presentation.workout_details.util

import android.app.AlertDialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.EditText
import androidx.core.view.isGone
import com.joaoflaviofreitas.lealfit.R
import com.joaoflaviofreitas.lealfit.domain.model.Exercise
import com.joaoflaviofreitas.lealfit.domain.model.Workout

fun showWorkoutDialog(context: Context, workout: Workout, callback: (Workout) -> Unit) {
    val inflater = LayoutInflater.from(context)
    val dialogView = inflater.inflate(R.layout.custom_dialog_layout, null)
    val title = workout.name

    val nameEditText = dialogView.findViewById<EditText>(R.id.nameEditText)
    val dateEditText = dialogView.findViewById<EditText>(R.id.dateEditText)
    val descriptionEditText = dialogView.findViewById<EditText>(R.id.descriptionEditText)

    descriptionEditText.setHint("Description")
    dateEditText.isGone = false
    dateEditText.setupDateInputFormat()
    nameEditText.setText(workout.name)
    dateEditText.setText(workout.date)
    descriptionEditText.setText(workout.description)

    val builder = AlertDialog.Builder(context)
    builder.setView(dialogView)
    builder.setTitle(title)
    builder.setPositiveButton("OK") { dialog, _ ->
        val name = nameEditText.text.toString()
        val date = dateEditText.text.toString()
        val description = descriptionEditText.text.toString()

        if (checkWorkoutFields(nameEditText, dateEditText)) {
            callback(Workout(workout.uid, name, date, description, workout.exercises))
            dialog.dismiss()
        }
    }
    builder.setNegativeButton("Cancel") { dialog, _ ->
        dialog.dismiss()
    }

    val dialog = builder.create()
    dialog.show()
}

fun showExerciseDialog(context: Context, exercise: Exercise, callback: (Exercise) -> Unit) {
    val inflater = LayoutInflater.from(context)
    val dialogView = inflater.inflate(R.layout.custom_dialog_layout, null)
    val title = exercise.name

    val nameEditText = dialogView.findViewById<EditText>(R.id.nameEditText)
    val dateEditText = dialogView.findViewById<EditText>(R.id.dateEditText)
    val descriptionEditText = dialogView.findViewById<EditText>(R.id.descriptionEditText)

    descriptionEditText.setHint("Observations")
    dateEditText.isGone = true
    nameEditText.setText(exercise.name)
    descriptionEditText.setText(exercise.observations)

    val builder = AlertDialog.Builder(context)
    builder.setView(dialogView)
    builder.setTitle(title)
    builder.setPositiveButton("OK") { dialog, _ ->
        val name = nameEditText.text.toString()
        val observations = descriptionEditText.text.toString()

        if (checkExerciseFields(nameEditText)) {

            callback(Exercise(name, exercise.image, observations))
            dialog.dismiss()
        }

        dialog.dismiss()
    }
    builder.setNegativeButton("Cancel") { dialog, _ ->
        dialog.dismiss()
    }

    val dialog = builder.create()
    dialog.show()
}


fun EditText.setupDateInputFormat() {
    val textWatcher = object : TextWatcher {
        private var isEditing = false

        override fun afterTextChanged(s: Editable?) {
            if (isEditing) return

            isEditing = true
            try {
                val input = s.toString()
                if (input.length == 3 || input.length == 6) {
                    val lastChar = input[input.length - 1]
                    if (lastChar != '/') {
                        s?.insert(input.length - 1, "/")
                    }
                } else if (input.length > 10) {
                    // Limit the length to 10 characters
                    s?.replace(10, input.length, "")
                }
            } finally {
                isEditing = false
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // No action needed
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // No action needed
        }
    }

    addTextChangedListener(textWatcher)
}

fun checkWorkoutFields(name: EditText, date: EditText): Boolean {
    var valid = false
    if (name.text.isEmpty()) {
        name.setError("Enter a name.", null)
        name.requestFocus()
    }
    if (date.text.isEmpty()) {
        date.setError("Please put the Workout Date", null)
        date.requestFocus()
    } else valid = true

    return valid
}

fun checkExerciseFields(name: EditText): Boolean {
    var valid = false
    if (name.text.isEmpty()) {
        name.setError("Enter a name.", null)
        name.requestFocus()
    } else valid = true

    return valid
}


