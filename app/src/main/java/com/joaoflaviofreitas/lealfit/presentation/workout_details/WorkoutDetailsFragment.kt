package com.joaoflaviofreitas.lealfit.presentation.workout_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.joaoflaviofreitas.lealfit.R
import com.joaoflaviofreitas.lealfit.adapters.ExerciseAdapter
import com.joaoflaviofreitas.lealfit.databinding.FragmentWorkoutDetailsBinding
import com.joaoflaviofreitas.lealfit.domain.model.Exercise
import com.joaoflaviofreitas.lealfit.model.StateUI
import com.joaoflaviofreitas.lealfit.presentation.workout_details.util.showExerciseDialog
import com.joaoflaviofreitas.lealfit.presentation.workout_details.util.showWorkoutDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class WorkoutDetailsFragment : Fragment() {


    private var _binding: FragmentWorkoutDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WorkoutDetailsViewModel by viewModels()

    private val args: WorkoutDetailsFragmentArgs by navArgs()
    private lateinit var exerciseAdapter: ExerciseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        setupAdapter()
        setData()
        observeWorkout()
    }

    override fun onResume() {
        super.onResume()
        viewModel.setWorkoutFromArgs(args.Workout)

    }

    private fun setupAdapter() {
        exerciseAdapter = ExerciseAdapter(clickThreeDots = { exercise, imageView ->
            openExerciseDropdownMenu(exercise, imageView)
        })
        binding.rv.adapter = exerciseAdapter
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun deleteExercise(exerciseToDelete: Exercise) {
        viewModel.deleteExercise(exerciseToDelete, args.Workout)
    }

    private fun setData() {
        binding.tb.setTitle(args.Workout.name)
    }

    private fun setClickListeners() {
        binding.threeDots.setOnClickListener {
            openWorkoutDropdownMenu()
        }
        binding.fab.setOnClickListener {
            navigateToAddExerciseFragment(null)
        }
        binding.tb.setNavigationOnClickListener {
            popBackStack()
        }
    }

    private fun navigateToAddExerciseFragment(exercise: Exercise?) {
        val action =
            WorkoutDetailsFragmentDirections.actionWorkoutDetailsFragmentToAddExerciseFragment(
                args.Workout.uid ?: "", exercise
            )
        findNavController().navigate(action)
    }

    private fun popBackStack() {
        findNavController().popBackStack()
    }

    private fun openWorkoutDropdownMenu() {
        var workout = args.Workout

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED)  {
               viewModel.uiState.collectLatest {
                   if(it is StateUI.Processed) {
                       workout = it.data
                   }
               }
            }
        }

        val imageView = binding.threeDots
        val popupMenu = PopupMenu(context, imageView)
        popupMenu.inflate(R.menu.top_app_bar)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.edit -> {
                    showWorkoutDialog(requireContext(), workout) { newWorkout ->
                        viewModel.updateWorkout(args.Workout.uid ?: "", newWorkout)
                    }
                    true
                }

                R.id.delete -> {
                    deleteWorkout()
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }

    private fun openExerciseDropdownMenu(exerciseToDeleteOrUpdate: Exercise, imageView: ImageView) {
        val popupMenu = PopupMenu(context, imageView)
        popupMenu.inflate(R.menu.top_app_bar)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.edit -> {
                    showExerciseDialog(
                        requireContext(),
                        exerciseToDeleteOrUpdate,
                    ) { newExercise ->
                        viewModel.updateExercise(
                          exerciseToDeleteOrUpdate,
                            newExercise
                        )
                        exerciseAdapter.notifyItemChanged(args.Workout.exercises?.indexOf(exerciseToDeleteOrUpdate)?: 0)
                    }
                    true
                }

                R.id.delete -> {
                    deleteExercise(exerciseToDeleteOrUpdate)
                    exerciseAdapter.notifyItemRemoved(args.Workout.exercises?.indexOf(exerciseToDeleteOrUpdate)?: 0)
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }

    private fun deleteWorkout() {

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.deleteWorkout(args.Workout.uid ?: "").collect { result ->
                        when (result) {
                            is StateUI.Processed -> {
                                withContext(Dispatchers.Main) {
                                    navigateToWorkoutFragment()
                                }
                            }

                            is StateUI.Processing -> {
                                withContext(Dispatchers.Main) {
                                    showToastLengthLong(result.message)
                                }
                            }

                            is StateUI.Error -> {
                                withContext(Dispatchers.Main) {
                                    showToastLengthLong(result.message)
                                }
                            }

                            is StateUI.Idle -> {
                            }
                        }
                    }
                }
            }
        }
    }

    private fun navigateToWorkoutFragment() {
        val action =
            WorkoutDetailsFragmentDirections.actionWorkoutDetailsFragmentToWorkoutFragment()
        findNavController().navigate(action)
    }

    private fun showToastLengthLong(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG)
            .show()
    }

    private fun observeWorkout() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { result ->
                        when (result) {
                            is StateUI.Processed -> {
                                withContext(Dispatchers.Main) {
                                    binding.pb.isGone = true
                                    binding.thereAreNoExercises.isVisible =
                                        result.data.exercises?.isEmpty()
                                            ?: true
                                    binding.tb.setTitle(result.data.name)
                                    submitUpdatedWorkout(result.data.exercises)
                                }
                            }

                            is StateUI.Processing -> {
                                withContext(Dispatchers.Main) {
                                    binding.pb.isGone = false
                                }
                            }
                            is StateUI.Error -> {
                                withContext(Dispatchers.Main) {
                                    binding.pb.isGone = true
                                    binding.thereAreNoExercises.isVisible = true
                                    binding.thereAreNoExercises.text = result.message
                                }
                            }

                            is StateUI.Idle -> {
                                withContext(Dispatchers.Main) {
                                    binding.pb.isGone = true
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun submitUpdatedWorkout(exercises: List<Exercise>?) {
        exerciseAdapter.submitList(exercises)
    }

}