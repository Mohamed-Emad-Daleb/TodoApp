package com.route.todoapp.ui.taps.tasks_list

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.route.todoapp.HomeActivity
import com.route.todoapp.databinding.UpdateTaskBinding
import com.route.todoapp.model.MyDataBase
import com.route.todoapp.model.Task
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class UpdateTask : AppCompatActivity() {
    lateinit var ViewBinding:UpdateTaskBinding
    private lateinit var task:Task
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewBinding=UpdateTaskBinding.inflate(layoutInflater)
        setContentView(ViewBinding.root)
        task=((intent.getSerializableExtra("Task")as?Task)!!)
        showData(task)
        ViewBinding.editBut.setOnClickListener {
            upDateToDo()
        }
    }
    fun validit():Boolean {
        var valid=true
        var title=ViewBinding.containerTitle.editText?.text.toString()
        val des=ViewBinding.containerDescription.editText?.text.toString()
        if(title.isNullOrBlank()){
            ViewBinding.containerTitle.error="please enter the title"
            valid=false
        }
        else{
            ViewBinding.containerTitle.error=null
        }
        if(des.isNullOrBlank()){
            ViewBinding.containerDescription.error="please enter the title"
            valid=false
        }
        else
            ViewBinding.containerDescription.error = null
            return valid
    }
    private fun upDateToDo() {
        if(validit()==false){
            return
        }
        task.title=ViewBinding.containerTitle.editText?.text.toString()
        task.description=ViewBinding.containerDescription.editText?.text.toString()
        MyDataBase.getInstance(this).tasksDao().apdateTask(task)
        showInsertDialog()
        startActivity(Intent(this,HomeActivity::class.java))
        finish()
    }

    private fun showInsertDialog() {
        val alertDialogBuilder=AlertDialog.Builder(this)
            .setMessage("Update Successfully")
            .setPositiveButton("ok"){
                dialog,which->dialog.dismiss()
            }
        alertDialogBuilder.show()
    }

    private fun showData(task: Task) {
        ViewBinding.containerTitle.editText?.setText(task.title)
        ViewBinding.containerDescription.editText?.setText(task.description)
        val date=convertLongToTime(task.dateTime)
        ViewBinding.date.text= date.toString()
    }
    private fun convertLongToTime(dateTime: Long?): Any {
        val dateTime= Date(dateTime!!)
        val format=SimpleDateFormat("YYYY-MM-dd")
        return format.format(dateTime)
    }
}