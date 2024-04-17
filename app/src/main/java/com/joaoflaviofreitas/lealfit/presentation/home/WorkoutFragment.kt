package com.joaoflaviofreitas.lealfit.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.joaoflaviofreitas.lealfit.R
import com.joaoflaviofreitas.lealfit.adapters.WorkoutAdapter
import com.joaoflaviofreitas.lealfit.databinding.FragmentWorkoutBinding
import com.joaoflaviofreitas.lealfit.domain.model.Workout
import com.joaoflaviofreitas.lealfit.model.StateUI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class WorkoutFragment : Fragment() {

    private val viewModel: WorkoutViewModel by viewModels()
    private var _binding: FragmentWorkoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var workoutAdapter: WorkoutAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        observeList()
        setupAdapters()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getWorkouts()
    }

    private fun setupAdapters() {
        workoutAdapter = WorkoutAdapter { workout ->
            navigateToWorkoutDetailsFragment(workout)
        }
        binding.rv.adapter = workoutAdapter
    }

    private fun navigateToWorkoutDetailsFragment(workout: Workout) {
        val action =
            WorkoutFragmentDirections.actionWorkoutFragmentToWorkoutDetailsFragment(workout)
        findNavController().navigate(action)
    }

    private fun observeList() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { result ->
                    when (result) {
                        is StateUI.Processed -> {
                            withContext(Dispatchers.Main) {
                                binding.pb.isGone = true
                                binding.thereAreNoExercicies.isVisible = result.data.isEmpty()
                                submitList(result.data)
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
                                binding.thereAreNoExercicies.isVisible = true
                                binding.thereAreNoExercicies.text = result.message
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

    private fun submitList(workouts: List<Workout>) {
        workoutAdapter.submitList(workouts)
    }

    private fun setClickListeners() {
        binding.fab.setOnClickListener {
            navigateToAddWorkoutFragment()
        }
        binding.profileImg.setOnClickListener {
            openDropdownMenu()
        }
    }

    private fun navigateToAddWorkoutFragment() {
        val action = WorkoutFragmentDirections.actionWorkoutFragmentToAddWorkoutFragment()
        findNavController().navigate(action)
    }

    private fun openDropdownMenu() {
        val imageView = binding.profileImg
        val popupMenu = PopupMenu(context, imageView)
        popupMenu.inflate(R.menu.profile_menu_bar)


        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.signout -> {
                    signOut()
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }

    private fun signOut() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.signOut().collect { result ->
                    when (result) {
                        is StateUI.Processed -> {
                            withContext(Dispatchers.Main) {
                                navigateToWelcomeFragment()
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

    private fun navigateToWelcomeFragment() {
        val action = WorkoutFragmentDirections.actionWorkoutFragmentToWelcomeFragment()
        findNavController().navigate(action)
    }
}