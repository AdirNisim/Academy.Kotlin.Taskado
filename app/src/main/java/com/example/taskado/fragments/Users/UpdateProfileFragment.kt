package com.example.taskado.fragments.Users

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.taskado.Models.User
import com.example.taskado.R
import com.example.taskado.Validator.Validator
import com.example.taskado.databinding.UpdateProfilePageBinding
import com.example.taskado.fragments.Users.ViewModels.UpdateProfileViewModel
import com.google.firebase.storage.FirebaseStorage

import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared
import kotlinx.coroutines.runBlocking


@AndroidEntryPoint
class UpdateProfileFragment: Fragment() {
    private var binding: UpdateProfilePageBinding by autoCleared()
    private val viewModel: UpdateProfileViewModel by viewModels()
    private lateinit var currentUser: User

    //TODO: storage Ref
    private val storageRef = FirebaseStorage.getInstance().reference
    private lateinit var permissionLauncer: ActivityResultLauncher<Array<String>>
    private var isReadPermissionGranted :Boolean = false
    //TODO: storage Ref end
    private var imageUri: Uri? = null

    private val pickImageLauncher : ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            binding.ivProfileUserImage.setImageURI(it)
            if (it != null) {
                requireActivity().contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                imageUri = it
            }
            //TODO: add image to user + upload it Storage
            currentUser = viewModel.getUser()

            val imageRef = storageRef.child("${currentUser.user_id}")
            val uploadTask = imageUri?.let { it1 -> imageRef.putFile(it1) }

            if (uploadTask != null) {
                uploadTask.addOnSuccessListener {
                    Toast.makeText(requireContext(), "Image uploaded successfully", Toast.LENGTH_SHORT).show()
                    // Image was successfully uploaded
                }.addOnFailureListener {
                    // Upload failed
                    Toast.makeText(requireContext(), "Image upload failed", Toast.LENGTH_SHORT).show()
                }
                //TODO: add image to user + upload it Storage END
            }


        }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //TODO: lior's code
        permissionLauncer = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            isReadPermissionGranted = permissions[android.Manifest.permission.READ_EXTERNAL_STORAGE] ?: false
        }
        requestPermission()
        //TODO: lior's code end
        currentUser = viewModel.getUser()
        // setting binding to inflater and navigation.3
        binding = UpdateProfilePageBinding.inflate(inflater,container,false)
        readData()
        //Open gallery to change image
        binding.ivProfileUserImage.setOnClickListener {
            //TODO: lior's code
            if(isReadPermissionGranted) {
                pickImageLauncher.launch(arrayOf("image/*"))
            }else{
                goToSettings()
            }








            //TODO: lior's code end
        }
        binding.btnUpdateProfile.setOnClickListener {
            updateProfile()
        }
        return binding.root
    }
    //TODO: lior's edit
    private fun updateProfile() {
        //show dialog confirm update
        val name = binding.inputMyProfilefullName.text.toString()
        val email = binding.inputMyProfilefullEmail.text.toString()
        val phone = binding.inputMyProfileFullPhone.text.toString()
        if (imageUri != null) {
            currentUser.user_image = imageUri.toString()
        }
        when (true) {
            (!Validator.isFullNameValid(name)) -> binding.inputMyProfilefullName.error =
                "full name must contain only English letters"
            (!Validator.isPhoneNumberValid(phone)) -> binding.inputMyProfileFullPhone.error =
                "Phone must be 10 digits and start with 05"
            else -> {
                currentUser.user_full_name = name
                currentUser.user_email = email
                currentUser.user_phone = phone
                runBlocking { viewModel.updateExistingUser(currentUser) }
                findNavController().navigate(R.id.action_updateProfileFragment_to_userPageFragment)
            }
        }

    }
    //TODO: lior's edit end

    private fun readData()  {
        var currentEmail = currentUser.user_email;
        var currentName = currentUser.user_full_name
        var currentorganization = currentUser.user_organization
        var currentPhoneNumber = currentUser.user_phone
        var currentImage = currentUser.user_image


        //Handle the full name
        binding.myProfileFullName.hint=""
        binding.inputMyProfilefullName.setText(currentName)
        //Handle the email
        binding.myProfileEmail.hint=""
        binding.inputMyProfilefullEmail.setText(currentEmail)
        //Handle the Organization
        binding.myProfileOrganization.hint=""
        binding.inputMyProfilefullOrganization.setText(currentorganization)
        //Handle the phone
        binding.myProfilePhone.hint=""
        binding.inputMyProfileFullPhone.setText(currentPhoneNumber)
        Glide.with(requireContext()).load(currentImage.toString()).into(binding.ivProfileUserImage)



    }
    //TODO: lior's code
    private fun requestPermission() {
        isReadPermissionGranted = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        val permissionRequest: MutableList<String> = ArrayList()
        if (!isReadPermissionGranted) {
            permissionRequest.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (permissionRequest.isNotEmpty()) {
            permissionLauncer.launch(permissionRequest.toTypedArray())
        }
    }

    //TODO: lior's code end


    private fun goToSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", "com.example.taskado", null)
        intent.data = uri
        startActivity(intent)
    }



}


