package com.example.taskado.fragments.Guest


import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.taskado.databinding.LoginPageLayoutBinding
import androidx.navigation.fragment.findNavController
import com.example.taskado.R
import com.example.taskado.Validator.Validator
import com.example.taskado.fragments.Guest.ViewModels.LoginPageViewModel
import com.example.taskado.local_db.viewModelRepo


import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.finalkotlinproject.utils.Loading
import il.co.syntax.finalkotlinproject.utils.Success
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared

@AndroidEntryPoint
class LoginPageFragment : Fragment() {
    private var binding: LoginPageLayoutBinding by autoCleared()
    private val viewModel: LoginPageViewModel by viewModels()
    private val localViewModel: viewModelRepo by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // setting binding to inflater and navigation
        binding = LoginPageLayoutBinding.inflate(inflater,container,false)

        binding.registerPage.setOnClickListener {
            findNavController().navigate(R.id.action_loginPageFragment_to_registerPageFragment)
        }
        binding.loginButton.setOnClickListener {
            val email: String = binding.inputLoginEmail.text.toString()
            val password: String = binding.inputPassword.text.toString()
            when(true) {
                (!Validator.isEmailValid(email)) ->  binding.titleEmail.error = "This Email is not valid"
                (password.isNullOrBlank()) ->  binding.titlePassword.error = "Please enter a password"
                else -> {
                    if(validateForm(email,password)) {
                        viewModel.signIn(email,password)
                    }
                }
            }
        }
        return binding.root
    }


    private fun validateForm(email: String,password: String) :Boolean {
        return when{
            TextUtils.isEmpty(email) -> {
                Toast.makeText(context, "Please enter an email address.",
                    Toast.LENGTH_LONG).show()
                false
            }
            else->true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.userSignInStatus.observe(viewLifecycleOwner) {
            when (it.status) {
                is Success -> {
                    val currentUser = viewModel.currentUser.value

                    findNavController().navigate(R.id.action_loginPageFragment_to_userPageFragment)
                }
                is Loading -> {
                    Toast.makeText(requireContext(), "Connecting", Toast.LENGTH_SHORT).show()
                }
                else -> {  Toast.makeText(requireContext(), "Check Your inputs", Toast.LENGTH_SHORT).show() }
            }
        }
    }

}


