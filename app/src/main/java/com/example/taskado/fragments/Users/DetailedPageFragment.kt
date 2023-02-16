package com.example.taskado.fragments.Users
import MiniTaskAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.taskado.Adapters.UpdateMiniTaskAdapter
import com.example.taskado.Models.Task
import com.example.taskado.Models.User
import com.example.taskado.R
import com.example.taskado.databinding.DetailedTicketPageBinding
import com.example.taskado.dataclasses.STATUS
import com.example.taskado.fragments.Users.ViewModels.DetailedPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared


@AndroidEntryPoint
class DetailedPageFragment : Fragment() {
    private var binding: DetailedTicketPageBinding by autoCleared()
    private val viewModel: DetailedPageViewModel by viewModels()
    private lateinit var currentUser: User
    private lateinit var task_id: String
    private lateinit var updatedTask: Task
    private lateinit var miniAdapter: UpdateMiniTaskAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        task_id = arguments?.get("TaskId").toString()
        updatedTask = viewModel.getTask(task_id)
        binding = DetailedTicketPageBinding.inflate(inflater,container,false)
        currentUser = viewModel.getUser()

        binding.taskUpdae.setOnClickListener {
            var counter = 0
            for (item in updatedTask.task_mini_array) {
                if (item.status == STATUS.FINISHED)
                    counter++
            }

            updatedTask.mini_task_done = counter
            viewModel.UpdateTask(updatedTask)
            findNavController().navigate(R.id.action_detailedPageFragment_to_userPageFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.taskCreator.text = updatedTask.task_creator
        binding.taskDescription.text = updatedTask.task_description
        binding.taskStartDate.text = updatedTask.task_start_date
        binding.taskEndDate.text = updatedTask.task_end_date
        binding.taskMinitaskAmount.text = updatedTask.task_mini_array.size.toString()
        binding.taskTitle.text = updatedTask.task_title
        when (updatedTask.task_done) {
           false -> binding.taskStatus.text = getResources().getString(R.string.progress);
            else -> binding.taskStatus.text = getResources().getString(R.string.done);
        }
        binding.taskMiniTasksRecycler.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.taskMiniTasksRecycler.setHasFixedSize(true)

        init()
    }

    private fun init() {
        if (binding.taskMiniTasksRecycler.adapter == null) {
            binding.taskMiniTasksRecycler.setHasFixedSize(true)
            binding.taskMiniTasksRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            val snapHelper: SnapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(binding.taskMiniTasksRecycler)
            miniAdapter = UpdateMiniTaskAdapter(updatedTask.task_mini_array) {
                when(it.status) {
                    1 -> {
                        updatedTask.task_mini_array[it.adapterPosition].taskCloser = currentUser.user_full_name
                        updatedTask.task_mini_array[it.adapterPosition].status = STATUS.FINISHED
                    }
                    0-> {
                        updatedTask.task_mini_array[it.adapterPosition].taskCloser = "No One"
                        updatedTask.task_mini_array[it.adapterPosition].status = STATUS.NOT_FINISHED
                    }

                }
                miniAdapter.notifyDataSetChanged()
            }
            binding.taskMiniTasksRecycler.adapter = miniAdapter
        } else {
            miniAdapter.notifyDataSetChanged()
        }
    }
}


