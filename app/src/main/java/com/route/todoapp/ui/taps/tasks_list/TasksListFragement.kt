package com.route.todoapp.ui.taps.tasks_list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import com.route.todoapp.base.BaseFragement
import com.route.todoapp.databinding.FragementTasksListBinding
import com.route.todoapp.model.MyDataBase
import com.route.todoapp.model.Task
import java.util.Calendar

class TasksListFragement:BaseFragement() {
    lateinit var viewBiniding: FragementTasksListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBiniding= FragementTasksListBinding.inflate(inflater,container,false)
        return viewBiniding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        taskAdapter.onItemClicked=object :OnItemClicked{
            override fun onItemClick(task: Task) {
                showMesssage("what do you want","Update",
                    {
                        _,dialog->updateTodoTask(task)
                    },
                    "Make Done !",
                    {
                        _,dialog->makeDone(task)
                    }
                    )
            }
        }

    }



    private fun makeDone(task: Task) {
        task.isDone=true
        MyDataBase.getInstance(requireContext()).tasksDao()
            .apdateTask(task)
        /////////////////////////////
        refreshRecyclerView()
    }

    private fun refreshRecyclerView() {
        taskAdapter.bindTasks(MyDataBase.getInstance(requireContext()).tasksDao().getAllTask().toMutableList())
        taskAdapter.notifyDataSetChanged()
    }

    private fun updateTodoTask(task: Task) {
        var intent=Intent(requireContext(),UpdateTask::class.java)
        intent.putExtra("Task",task)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        loadTasks()
    }

    fun loadTasks() {
        context?.let {
            val tasks= MyDataBase.getInstance(it)
                .tasksDao()
                .getTaskByDay(selectedDate.timeInMillis)
            taskAdapter.bindTasks(tasks.toMutableList())
        }

    }

    private val taskAdapter=TasksAdapter(null)
    val selectedDate=Calendar.getInstance()
    init {
        selectedDate.set(Calendar.HOUR,0)
        selectedDate.set(Calendar.MINUTE,0)
        selectedDate.set(Calendar.SECOND,0)
        selectedDate.set(Calendar.MILLISECOND,0)
    }
    private fun initViews() {
        viewBiniding.recyclerView.adapter=taskAdapter
        taskAdapter.onItemDeleteClicked= OnItemDeleteClicked { position, task ->
            deleteTaskFromDataBase(task)
            taskAdapter.taskDelet(task)
        }
        viewBiniding.calendarView.setSelectedDate(
            CalendarDay.today()
        )
       viewBiniding.calendarView.setOnDateChangedListener(OnDateSelectedListener{
           widget, date, selected ->
           if(selected) {
               // reload tasks in this selected day
               selectedDate.set(Calendar.YEAR,date.year)
               selectedDate.set(Calendar.MONTH,date.month-1)
               selectedDate.set(Calendar.YEAR,date.year)
               selectedDate.set(Calendar.DAY_OF_MONTH,date.day)
               loadTasks()
           }
       })
    }

    private fun deleteTaskFromDataBase(task: Task) {
        MyDataBase.getInstance(requireContext()).tasksDao().deleteTask(task)
    }
}