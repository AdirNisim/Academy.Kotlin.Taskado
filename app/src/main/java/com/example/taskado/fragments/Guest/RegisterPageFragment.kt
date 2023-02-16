package com.example.taskado.fragments.Guest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.taskado.R
import com.example.taskado.Models.User
import com.example.taskado.Validator.Validator
import com.example.taskado.databinding.RegisterPageLayoutBinding
import com.example.taskado.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared
import com.example.taskado.fragments.Guest.ViewModels.RegisterPageViewModel
import il.co.syntax.finalkotlinproject.utils.Loading
import il.co.syntax.finalkotlinproject.utils.Success

@AndroidEntryPoint
class RegisterPageFragment : Fragment() {
    private var binding : RegisterPageLayoutBinding by autoCleared()
    private val viewModel: RegisterPageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegisterPageLayoutBinding.inflate(inflater,container,false)
        binding.submitButton.setOnClickListener {
            registerUser()
        }
        binding.loginPage.setOnClickListener {
            findNavController().navigate(R.id.action_registerPageFragment_to_loginPageFragment)
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userRegistrationStatus.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> {
                    binding.submitButton.isEnabled = false
                }
                is Success -> {
                    Toast.makeText(requireContext(),"Registration successful",Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerPageFragment_to_loginPageFragment)
                }
                 is Error -> {
                       binding.submitButton.isEnabled = true
                 }
                is il.co.syntax.finalkotlinproject.utils.Error ->
                { Toast.makeText(requireContext(),"Registration error",Toast.LENGTH_SHORT).show()
                    binding.submitButton.isEnabled = true

                }
            }
        }
    }


    private fun registerUser() {
        val fullName: String = binding.inputFullname.text.toString()
        val password: String = binding.inputPassword.text.toString()
        val email: String = binding.inputEmail.text.toString()
        val organization: String = binding.inputOrganization.text.toString().uppercase()
        val phone: String = binding.inputPhone.text.toString()
        val image: String = Constants.DEFAULTIMAGEURL

        when(true) {
            //TODO: lior's edit
            (!Validator.isFullNameValid(fullName)) -> binding.titleFullname.error = "full name must contain only English letters"
            (!Validator.isEmailValid(email)) -> binding.emailTitle.error = "Email must be this pattern: test@email.com"
            (!Validator.isValidPassword(password)) -> binding.titlePassword.error = "Password must be at least 8 characters, 1 lower 1 upper and special case"
            (!Validator.isPhoneNumberValid(phone)) -> binding.titlePhone.error = "Phone must be 10 digits and start with 05"
            organization.isNullOrBlank() -> binding.titleOrganization.error = "Organization mustn't be empty"
            //TODO: lior's edit end
            else -> {
                val newUser = User("newId",email,password,fullName,phone,image,organization)
                if(Validator.reregistration(newUser)) {
                    viewModel.createUser(newUser)
                }
            }
        }

    }
}