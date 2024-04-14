package com.joaoflaviofreitas.lealfit.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.joaoflaviofreitas.lealfit.databinding.FragmentWorkoutBinding
import com.joaoflaviofreitas.lealfit.ui.login.model.StateUI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class WorkoutFragment: Fragment() {

    private val viewModel: WorkoutViewModel by viewModels()
    private var _binding: FragmentWorkoutBinding? = null
    private val binding get() = _binding!!

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
    }

    private fun observeList() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect {result ->
                        when (result) {
                            is StateUI.Processed -> {
                                withContext(Dispatchers.Main) {
                                    binding.pb.isGone = true
                                    binding.thereAreNoExercicies.isVisible = result.data.exercises.isEmpty()
                                }
                            }
                            is StateUI.Processing -> { withContext(Dispatchers.Main) {
                                binding.pb.isGone = false
                            }
                            }
                            is StateUI.Error -> { }
                            is StateUI.Idle -> {}
                        }

                    }
                }
            }
        }
    }

    private fun setClickListeners() {
        TODO("Not yet implemented")
    }
}