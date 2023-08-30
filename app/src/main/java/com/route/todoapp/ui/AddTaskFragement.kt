package com.route.todoapp.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.route.todoapp.databinding.FragementAddTaskBinding
import com.route.todoapp.model.MyDataBase
import com.route.todoapp.model.Task
import java.util.Calendar

class AddTaskFragement :BottomSheetDialogFragment(){
   lateinit var viewBiniding:FragementAddTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBiniding= FragementAddTaskBinding.inflate(inflater,container,false)
        return  viewBiniding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBiniding.addTaskBut
            .setOnClickListener {
                creatTask()
            }
        viewBiniding.dateContener
            .setOnClickListener {
                showDateBikerDialog()
            }
    }
    val calender = Calendar.getInstance()
    private fun showDateBikerDialog() {
        context?.let {
            val dialog = DatePickerDialog(requireContext())
            dialog.setOnDateSetListener {
                    datePicker,day,month,year ->
                viewBiniding.date.setText(
                    "$day-${month+1}-$year"
                )
                calender.set(year,month,day)
                calender.set(Calendar.HOUR_OF_DAY,0)
                calender.set(Calendar.MINUTE,0)
                calender.set(Calendar.SECOND,0)
                //to ignor time
                calender.set(Calendar.MILLISECOND,0)
            }
            dialog.show()
        }
    }

    private fun valid(): Boolean {
        var isValid=true
        if(viewBiniding.title.text.toString().isNullOrBlank()){
            viewBiniding.titleContener.error="please enter title"
            isValid=false
        }
        else {
            viewBiniding.titleContener.error=null
        }
        if(viewBiniding.desc.text.toString().isNullOrBlank()){
            viewBiniding.desContener.error="please enter description"
            isValid=false
        }
        else {
            viewBiniding.desContener.error=null
        }
        if(viewBiniding.date.text.toString().isNullOrBlank()){
            viewBiniding.dateContener.error="please enter date"
            isValid=false
        }
        else {
            viewBiniding.dateContener.error=null
        }
        return isValid
    }

    private fun creatTask() {
        if(!valid()){
            return
        }
        val tast= Task(
            title = viewBiniding.title.text.toString(),
            description = viewBiniding.desc.text.toString(),
            dateTime= calender.timeInMillis
        )
        MyDataBase.getInstance(requireContext())
            .tasksDao()
            .insertTask(tast)
        onTaskAddListener?.onTaskAdded()
        dismiss()
    }

    var onTaskAddListener:OnTaskAddListener?=null

    fun interface OnTaskAddListener{
        fun onTaskAdded()
    }
}