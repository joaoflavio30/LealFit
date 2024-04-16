package com.joaoflaviofreitas.lealfit.presentation.add_exercicie

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.joaoflaviofreitas.lealfit.databinding.FragmentAddExerciseBinding
import com.joaoflaviofreitas.lealfit.domain.model.Exercise
import com.joaoflaviofreitas.lealfit.model.StateUI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

@AndroidEntryPoint
class AddExerciseFragment: Fragment() {

    private val viewModel: AddExerciseViewModel by viewModels()

    private var _binding: FragmentAddExerciseBinding? = null
    private val binding get() = _binding!!

    private var selectedImgUri: Uri? = null

    private val args: AddExerciseFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        observeResponse()
    }

    private fun setClickListeners() {
        binding.createBtn.setOnClickListener {
            if (checkFields()) createExercise()

        }
        binding.tb.setNavigationOnClickListener {
            popBackStack()
        }
        binding.image.setOnClickListener {
            if(checkIfUserHasPermission()) {
                showImagePickerDialog(requireContext())
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    showTheImportanceOfPermission()
                }
            }
        }
    }

    private fun observeResponse() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect {result ->
                        when (result) {
                            is StateUI.Processed -> {
                                withContext(Dispatchers.Main) {
                                    navigateToWorkoutFragment()
                                }
                            }
                            is StateUI.Processing -> { withContext(Dispatchers.Main) {
                                showToastLengthLong(result.message)
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

    private fun navigateToWorkoutFragment() {
        val action = AddExerciseFragmentDirections.actionAddExerciseFragmentToWorkoutFragment()
        findNavController().navigate(action)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value
            }
            if (granted) {
                showImagePickerDialog(requireContext())
            } else {
                showToastLengthLong("Permission denied")
            }
        }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun showTheImportanceOfPermission() {
        AlertDialog.Builder(requireContext())
            .setTitle("Permission required")
            .setMessage("permission required for photo feature")
            .setPositiveButton("Ok") { _, _ ->
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_MEDIA_IMAGES,
                    ),
                )
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    private fun showImagePickerDialog(context: Context) {
        val options = arrayOf("Gallery", "Camera", "Cancel")

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Choose an Option")
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> openGallery()
                1 -> openCamera()
                2 -> dialog.dismiss()
            }
        }
        builder.show()
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        imageContract.launch(intent)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile: File? = try {
            createImageFile()
        } catch (ex: IOException) {
            null
        }
        photoFile?.also {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                "com.joaoflaviofreitas.lealfit.fileprovider",
                it
            )
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            imageContract.launch(intent)
        }
    }


    private fun popBackStack() {
        findNavController().popBackStack()
    }
    private fun createExercise() {
        val exercise = Exercise(
            name = binding.inputName.text.toString(),
            observations = binding.inputObservation.text.toString(),
        )
        viewModel.createExercise(exercise, args.workoutUid, selectedImgUri)
    }

    private fun checkFields(): Boolean {
        val name = binding.inputName.text.toString()
        var valid = false
        if (name.isEmpty()) {
            binding.inputName.setError("Enter a name.", null)
            binding.inputName.requestFocus()
        }
       else valid = true

        return valid
    }

    private fun showToastLengthLong(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG)
            .show()
    }

    private fun checkIfUserHasPermission(): Boolean {
        return when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA,
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_MEDIA_IMAGES,
            ) == PackageManager.PERMISSION_GRANTED -> {
                true
            }
            else -> {false}
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            selectedImgUri = absolutePath.toUri()
        }
    }

    private val imageContract = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->
        if(result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            selectedImgUri = uri
            selectedImgUri?.let {
               binding.image.setImageURI(selectedImgUri)
            }
        }
        }
}