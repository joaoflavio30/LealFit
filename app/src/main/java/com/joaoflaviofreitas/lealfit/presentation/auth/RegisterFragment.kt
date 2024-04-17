package com.joaoflaviofreitas.lealfit.presentation.auth

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.joaoflaviofreitas.lealfit.databinding.FragmentRegisterBinding
import com.joaoflaviofreitas.lealfit.domain.model.Account
import com.joaoflaviofreitas.lealfit.model.StateUI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        observeResponse()
    }

    private fun setClickListeners() {
        binding.registerButton.setOnClickListener {
            registerIfFieldsAreValid()
        }
        binding.loginButton.setOnClickListener {
            navigateToSignIn()
        }

    }

    private fun observeResponse() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { result ->
                    when (result) {
                        is StateUI.Processed -> {
                            withContext(Dispatchers.Main) {
                                navigateToSignIn()
                            }
                        }

                        is StateUI.Processing -> {
                            withContext(Dispatchers.Main) {
                                showToastLengthLong(result.message)
                            }
                        }

                        is StateUI.Error -> {}
                        is StateUI.Idle -> {}
                    }

                }
            }
        }
    }

    private fun navigateToSignIn() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        findNavController().navigate(action)
    }

    private fun registerIfFieldsAreValid() {
        if (checkFields()) registerUser()
    }

    private fun registerUser() {
        viewModel.signup(
            Account(
                binding.email.text.toString(),
                binding.firstPassword.text.toString()
            )
        )
    }

    private fun checkFields(): Boolean {
        val email = binding.email.text.toString()
        val password = binding.firstPassword.text.toString()
        val confirmPassword = binding.secondPassword.text.toString()
        var valid = false

        when {
            email.isEmpty() -> {
                binding.email.setError("Enter email address.", null)
                binding.email.requestFocus()
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.email.setError("Enter valid email address.", null)
                binding.email.requestFocus()
            }

            password.isEmpty() -> {
                binding.firstPassword.setError("Enter password.", null)
                binding.firstPassword.requestFocus()
            }

            password.length <= 6 -> {
                binding.firstPassword.setError("Password length must be > 6 characters.", null)
                binding.firstPassword.requestFocus()
            }

            confirmPassword != password -> {
                binding.secondPassword.setError("Passwords must match.", null)
                binding.secondPassword.requestFocus()
            }

            else -> {
                valid = true
            }
        }

        return valid
    }

    private fun showToastLengthLong(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG)
            .show()
    }
}