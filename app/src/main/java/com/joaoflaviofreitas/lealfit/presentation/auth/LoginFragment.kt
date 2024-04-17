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
import com.joaoflaviofreitas.lealfit.databinding.FragmentLoginBinding
import com.joaoflaviofreitas.lealfit.domain.model.Account
import com.joaoflaviofreitas.lealfit.model.StateUI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        observeResponse()
    }

    private fun setClickListeners() {
        binding.loginButton.setOnClickListener {
            loginIfFieldsAreValid()
        }
        binding.registerButton.setOnClickListener {
            navigateToRegisterFragment()
        }

    }

    private fun navigateToRegisterFragment() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        findNavController().navigate(action)
    }

    private fun loginIfFieldsAreValid() {
        if (checkFields()) loginUser()
    }

    private fun loginUser() {
        viewModel.login(Account(binding.email.text.toString(), binding.password.text.toString()))
    }

    private fun checkFields(): Boolean {
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        var valid = false

        when {
            email.isEmpty() -> {
                binding.email.setError("Enter Email!", null)
                binding.email.requestFocus()
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.email.setError("Enter valid email address.", null)
                binding.email.requestFocus()
            }

            password.isEmpty() -> {
                binding.password.setError("Enter password.", null)
                binding.password.requestFocus()
            }

            password.length <= 6 -> {
                binding.password.setError("Password length must be > 6 characters.", null)
                binding.password.requestFocus()
            }

            else -> {
                valid = true
            }
        }
        return valid
    }

    private fun observeResponse() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { result ->
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

                        is StateUI.Error -> {}
                        is StateUI.Idle -> {}
                    }
                }
            }
        }
    }

    private fun navigateToWorkoutFragment() {
        val action = LoginFragmentDirections.actionLoginFragmentToWorkoutFragment()
        findNavController().navigate(action)
    }

    private fun showToastLengthLong(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG)
            .show()
    }
}


