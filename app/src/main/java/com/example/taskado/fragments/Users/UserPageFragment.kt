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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.taskado.Adapters.TaskAdapter
import com.example.taskado.Models.ClickedType
import com.example.taskado.Models.Task
import com.example.taskado.Models.User
import com.example.taskado.R
import com.example.taskado.databinding.UserPageLayoutBinding
import com.example.taskado.fragments.Users.ViewModels.UserPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.finalkotlinproject.utils.Error
import il.co.syntax.finalkotlinproject.utils.Loading
import il.co.syntax.finalkotlinproject.utils.Success
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared
import kotlinx.coroutines.runBlocking



@AndroidEntryPoint
class UserPageFragment : Fragment() {
    private var binding: UserPageLayoutBinding by autoCleared()
    private val viewModel: UserPageViewModel by viewModels()
    private lateinit var currentUser: User


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UserPageLayoutBinding.inflate(inflater,container,false)
        currentUser = viewModel.getUser()
        //update binding items
        binding.fullName.text = currentUser.user_full_name.toString()
        var profilepicture = currentUser.user_image
        binding.titleTasks.text = currentUser.user_organization.toString()
        Glide.with(requireContext()).load("$profilepicture").into(binding.updateProfile)


        binding.addTaskButton.setOnClickListener {
            findNavController().navigate(R.id.action_userPageFragment_to_addTaskFragment)
        }

        binding.tasksRecylcer.setOnClickListener {
            findNavController().navigate(R.id.action_userPageFragment_to_detailedPageFragment)
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tasksRecylcer.adapter = TaskAdapter() {
            val idBundle = Bundle()
            idBundle.putString("TaskId",it.task.task_id)
            val toast = Toast(context)
            val view = ImageView(context)
            when(it.type) {
                ClickedType.LongClick -> {
                    viewModel.deleteTask(it.task)
                }
                ClickedType.DoubleClick -> {
                    when(it.task.task_favorite){
                        1 -> {
                            it.task.task_favorite = 0
                            Toast.makeText(requireContext(), "Task Removed from Organization favorites", Toast.LENGTH_SHORT).show()
                        }
                        0 -> {
                            it.task.task_favorite = 1
                            Toast.makeText(requireContext(), "Task Added to Organization favorites", Toast.LENGTH_SHORT).show()
                        }
                    }
                    viewModel.UpdateTask(it.task)
                }
                else ->{
                    findNavController()?.navigate(
                        R.id.action_userPageFragment_to_detailedPageFragment,
                        idBundle)
                }
            }

        }
        binding.tasksRecylcer.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.tasksRecylcer.setHasFixedSize(true)
        viewModel.eventsStatus.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading ->{
                    Log.d("Recycler loading", "Trying to load data.")
                }
                is Success -> {
                    (binding.tasksRecylcer.adapter as TaskAdapter).updateTaskList(it.status.data!!)
                    viewModel.CleanAllTasksInRooam()
                    viewModel.setAllTasksInRoom(it.status.data!!)
                }
                is Error -> {
                    val list = mutableListOf<Task>()
                    (binding.tasksRecylcer.adapter as TaskAdapter).updateTaskList(list)
                    Log.d("Recycler view Error", "Error in getting data.")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.eventsStatus.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading ->{
                    Log.d("Recycler loading", "Trying to load data.")
                }
                is Success -> {
                    (binding.tasksRecylcer.adapter as TaskAdapter).updateTaskList(it.status.data!!)
                    viewModel.CleanAllTasksInRooam()
                    viewModel.setAllTasksInRoom(it.status.data!!)
                }
                is Error -> {
                    val list = mutableListOf<Task>()
                    (binding.tasksRecylcer.adapter as TaskAdapter).updateTaskList(list)
                    Log.d("Recycler view Error", "Error in getting data.")
                }
            }
        }
    }
}
