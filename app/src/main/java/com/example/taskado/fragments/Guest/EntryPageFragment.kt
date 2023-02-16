package com.example.taskado.fragments.Guest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.taskado.R
import com.example.taskado.databinding.EntryPageLayoutBinding


class EntryPageFragment : Fragment() {
    private var _binding: EntryPageLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // setting binding to inflater and navigation
        _binding = EntryPageLayoutBinding.inflate(inflater,container,false)
        binding.loginPageButton.setOnClickListener {
            findNavController().navigate(R.id.action_entryPageFragment_to_loginPageFragment)
        }
        binding.registerPageButton.setOnClickListener {
            findNavController().navigate(R.id.action_entryPageFragment_to_registerPageFragment)
        }
        var downAnimation = AnimationUtils.loadAnimation(context ,R.anim.ttb)
        var upAnimation = AnimationUtils.loadAnimation(context, R.anim.btn)
        binding.appTitle.startAnimation(downAnimation)
        binding.mainParagraph.startAnimation(downAnimation)
        binding.mainImage.startAnimation(upAnimation)
        binding.registerPageButton.startAnimation(upAnimation)
        binding.loginPageButton.startAnimation(upAnimation)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // binding memory cleaning
        _binding = null
    }
}