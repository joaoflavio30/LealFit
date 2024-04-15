package com.joaoflaviofreitas.lealfit.ui.login.data.mapper

import com.google.firebase.Timestamp
import com.joaoflaviofreitas.lealfit.ui.login.data.model.ExerciseDTO
import com.joaoflaviofreitas.lealfit.ui.login.data.model.WorkoutDTO
import com.joaoflaviofreitas.lealfit.ui.login.domain.model.Exercise
import com.joaoflaviofreitas.lealfit.ui.login.domain.model.Workout
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun mapWorkoutDTO(workoutDTO: WorkoutDTO): Workout = Workout(
    name = workoutDTO.name ?: "",
    description = workoutDTO.description ?: "",
    date = workoutDTO.date?.toDate()?.formatDate() ?: "",
    exercises = workoutDTO.exercises?.map { mapExerciseDTO(it) } ?: emptyList(),
    uid = workoutDTO.uid ?: ""
)

fun mapExerciseDTO(exerciseDTO: ExerciseDTO): Exercise = Exercise(
    name = exerciseDTO.name,
    image = exerciseDTO.image,
    observations = exerciseDTO.observations
)

fun mapWorkout(workout: Workout): WorkoutDTO {
    val date = workout.date.toDate()
    return WorkoutDTO(
        uid = workout.uid,
        name = workout.name,
        description = workout.description,
        date = date.toTimestamp(),
        exercises = workout.exercises?.map { mapExercise(it) }
    )
}

fun mapExercise(exercise: Exercise): ExerciseDTO = ExerciseDTO(
    name = exercise.name,
    image = exercise.image,
    observations = exercise.observations
)

fun Date.toTimestamp(): Timestamp = Timestamp(this)

fun Date.formatDate(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateFormat.format(this)
}

fun String.toDate(): Date {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateFormat.parse(this) ?: Date()
}