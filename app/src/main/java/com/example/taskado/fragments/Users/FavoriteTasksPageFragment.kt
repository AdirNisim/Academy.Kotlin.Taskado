package com.example.taskado.fragments.Users

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskado.Adapters.FavoriteTaskAdapter
import com.example.taskado.Adapters.TaskAdapter
import com.example.taskado.Models.ClickedType
import com.example.taskado.Models.Task
import com.example.taskado.Models.TaskR
import com.example.taskado.Models.User
import com.example.taskado.R
import com.example.taskado.databinding.MyFavoriteTasksBinding
import com.example.taskado.fragments.Users.ViewModels.FavoriteTasksPageViewModel
import com.example.taskado.fragments.Users.ViewModels.UserPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.finalkotlinproject.utils.Error
import il.co.syntax.finalkotlinproject.utils.Loading
import il.co.syntax.finalkotlinproject.utils.Success
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@AndroidEntryPoint
class FavoriteTasksPageFragment : Fragment() {
    private var binding: MyFavoriteTasksBinding by autoCleared()
    private val viewModel: FavoriteTasksPageViewModel by viewModels()
    private lateinit var TaskRList: MutableList<TaskR>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyFavoriteTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.favoriteTaskRecyclerview.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.favoriteTaskRecyclerview.setHasFixedSize(true)

        lifecycleScope.launch {
            TaskRList = viewModel.getAllFavoitesfromRoom()
            binding.favoriteTaskRecyclerview.adapter = FavoriteTaskAdapter(TaskRList)
        }
    }

    override fun onResume() {
        super.onResume()
        TaskRList = runBlocking { viewModel.getAllFavoitesfromRoom() }
        binding.favoriteTaskRecyclerview.adapter = FavoriteTaskAdapter(TaskRList)
    }
}
