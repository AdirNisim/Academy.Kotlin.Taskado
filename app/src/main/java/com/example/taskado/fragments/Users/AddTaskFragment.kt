package com.example.taskado.fragments.Users

import MiniTaskAdapter
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.taskado.Models.Task
import com.example.taskado.R
import com.example.taskado.databinding.AddTaskPageBinding
import com.example.taskado.dataclasses.MiniTask
import com.example.taskado.dataclasses.STATUS
import com.example.taskado.Models.User
import com.example.taskado.fragments.Users.ViewModels.AddTaskPageViewModel
import com.example.taskado.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.finalkotlinproject.utils.Error
import il.co.syntax.finalkotlinproject.utils.Loading
import il.co.syntax.finalkotlinproject.utils.Success
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared
import java.text.SimpleDateFormat
import com.example.taskado.Validator.Validator
import java.util.*

@AndroidEntryPoint
class AddTaskFragment :  Fragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var miniAdapter: MiniTaskAdapter
    private var binding: AddTaskPageBinding by autoCleared()
    private var Datepick: Int = 1
    private val formatter = SimpleDateFormat("MM,dd,yyyy",Locale.US)
    private val calender = Calendar.getInstance()
    private val MyMinitasks: MutableList<MiniTask> = mutableListOf()
    private var i = 0
    private val viewModel : AddTaskPageViewModel by viewModels()
    private lateinit var currentUser: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddTaskPageBinding.inflate(inflater,container,false)
        currentUser = viewModel.getUser()
        binding.startDate.setOnClickListener {
            Datepick = 1
            DatePickerDialog(
                requireContext(),
                this,
                calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        binding.endDate.setOnClickListener {
            Datepick = 2
            DatePickerDialog(
                requireContext(),
                this,
                calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        binding.AddMiniTask.setOnClickListener{
            val inflater = layoutInflater
            val dialogView = inflater.inflate(R.layout.mini_task_dialog, null)
            val builder = AlertDialog.Builder(requireContext())
            val recyclerview = binding.miniTaskRecyclerview
            builder.setView(dialogView)
            builder.setPositiveButton("Add") { _, _ ->
                // Perform action on "OK" button press
                var title_minitask =  dialogView.findViewById<EditText>(R.id.mini_task_name_input).text.toString()
                if(title_minitask.isNullOrBlank())  Toast.makeText(requireContext(), "Error ~ mini task title can't be empty", Toast.LENGTH_SHORT).show()
                else{
                    var new_mini_task = MiniTask(title_minitask,STATUS.NOT_FINISHED)
                    MyMinitasks.add(new_mini_task)
                    init()
                }
            }

            val alert = builder.create()
            alert.show()
        }

        binding.addTaskBtn.setOnClickListener {
            AddTask()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.addEventStatus.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> {
                    binding.addTaskBtn.isEnabled = false
                }
                is Success -> {
                    Toast.makeText(requireContext(), "Add Task-Successfull", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_addTaskFragment_to_userPageFragment)
                }
                is Error -> {
                    Toast.makeText(requireContext(), "Error ~ look at the logs.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun AddTask() {
        val task_title: String = binding.inputTaskName.text.toString()
        val task_start_date: String = binding.startDate.text.toString()
        val task_end_date: String = binding.endDate.text.toString()
        val task_description: String = binding.inputTaskDescription.text.toString()
        val task_mini_array = MyMinitasks
        val task_creator: String = currentUser.user_full_name.toString()

        if(!Validator.isValidTitle(task_title)) binding.inputTaskNameTitle.error = "must contain word, max 12 length"
        else if(task_start_date == "Start Date")  Toast.makeText(requireContext(), "You must choose start date", Toast.LENGTH_SHORT).show()
        else if(task_end_date == "End Date")  Toast.makeText(requireContext(), "You must choose End date", Toast.LENGTH_SHORT).show()
        else if(task_description.isNullOrBlank()) binding.inputTaskDescriptionTitle.error = "must contain word"
        else if(MyMinitasks.isEmpty()) Toast.makeText(requireContext(),"Must contain at least one mini task",Toast.LENGTH_SHORT).show()
        else if(!Validator.isValidDate(task_start_date, task_end_date)) Toast.makeText(requireContext(),"Start date must be before the end date",Toast.LENGTH_SHORT).show()

        if(MyMinitasks.isNotEmpty()
            && !task_title.isNullOrBlank()
            && Validator.isValidDate(task_start_date, task_end_date)
            && !task_description.isNullOrBlank()){
            var Mytask = Task(Constants.ORGANIZATIONINDEX,task_title,task_start_date,task_end_date,task_description,task_mini_array,task_creator,0,false,0)
            viewModel.addTask(Mytask,currentUser.user_organization.toString())
        }

    }


    private fun init() {
        if (binding.miniTaskRecyclerview.adapter == null) {
            binding.miniTaskRecyclerview.setHasFixedSize(true)
            binding.miniTaskRecyclerview.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            val snapHelper: SnapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(binding.miniTaskRecyclerview)
            miniAdapter = MiniTaskAdapter(MyMinitasks) {
                MyMinitasks.remove(it)
                miniAdapter.notifyDataSetChanged()
            }
            binding.miniTaskRecyclerview.adapter = miniAdapter
        } else {
            miniAdapter.notifyDataSetChanged()
        }
    }


     override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calender.set(year,month,dayOfMonth)
        displayFormatDate(calender.timeInMillis)
    }

    private  fun displayFormatDate(timestamp: Long) {
        if(Datepick == 1) {
            binding.startDate.text = formatter.format(timestamp)
        }
        else {
            binding.endDate.text = formatter.format(timestamp)
        }
    }



}