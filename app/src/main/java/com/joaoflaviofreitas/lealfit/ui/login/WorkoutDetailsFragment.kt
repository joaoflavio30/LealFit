package com.joaoflaviofreitas.lealfit.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.joaoflaviofreitas.lealfit.R
import com.joaoflaviofreitas.lealfit.databinding.FragmentWorkoutDetailsBinding
import com.joaoflaviofreitas.lealfit.ui.login.adapters.ExerciseAdapter
import com.joaoflaviofreitas.lealfit.ui.login.domain.model.Exercise
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkoutDetailsFragment : Fragment() {


    private var _binding: FragmentWorkoutDetailsBinding? = null
    private val binding get() = _binding!!

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
    }

    override fun onResume() {
        super.onResume()
        submitExercises()
    }

    private fun submitExercises() {
        exerciseAdapter.submitList(args.Workout.exercises)
    }

    private fun setupAdapter() {
        exerciseAdapter = ExerciseAdapter { exercise ->
            navigateToAddExerciseFragment(exercise)
        }
        binding.rv.adapter = exerciseAdapter
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setData() {
        binding.tb.setTitle(args.Workout.name)
    }

    private fun setClickListeners() {
        binding.threeDots.setOnClickListener {
            openDropdownMenu()
        }
        binding.tb.setNavigationOnClickListener {
            popBackStack()
        }
        binding.fab.setOnClickListener{
            navigateToAddExerciseFragment(null)
        }
    }

    private fun navigateToAddExerciseFragment(exercise: Exercise?) {
        val action = WorkoutDetailsFragmentDirections.actionWorkoutDetailsFragmentToAddExerciseFragment(args.Workout.uid?:"",exercise)
        findNavController().navigate(action)
    }

    private fun popBackStack() {
        findNavController().popBackStack()
    }

    private fun openDropdownMenu() {
        val imageView = binding.threeDots
        val popupMenu = PopupMenu(context, imageView)
        popupMenu.inflate(R.menu.top_app_bar)


        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.edit -> {
                    // Ação para o item 1
                    true
                }

                R.id.delete -> {
                    // Ação para o item 2
                    true
                }
                // Adicione casos para outros itens de menu conforme necessário
                else -> false
            }
        }
        popupMenu.show()
    }

    private fun showToastLengthLong(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG)
            .show()
    }
}