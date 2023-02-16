package com.example.taskado.fragments.Guest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.example.taskado.databinding.EntryAnimationLayoutBinding
import androidx.navigation.fragment.findNavController
import com.example.taskado.R
import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared


@AndroidEntryPoint
class EntryAnimationFragment :  Fragment() {
    private var binding: EntryAnimationLayoutBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EntryAnimationLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override  fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var downAnimation = AnimationUtils.loadAnimation(context ,R.anim.ttb)
        var upAnimation = AnimationUtils.loadAnimation(context, R.anim.btn)
        binding.taskadoTextView.startAnimation(downAnimation)
        binding.letsStart.startAnimation(upAnimation)

        binding.letsStart.setOnClickListener {
            findNavController().navigate(R.id.action_entryAnimationFragment_to_entryPageFragment)
        }


    }
}
