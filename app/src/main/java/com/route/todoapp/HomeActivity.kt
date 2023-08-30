package com.route.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.route.todoapp.databinding.ActivityHomeBinding
import com.route.todoapp.ui.AddTaskFragement
import com.route.todoapp.ui.taps.SettingsFragement
import com.route.todoapp.ui.taps.tasks_list.TasksListFragement

class HomeActivity : AppCompatActivity() {
    lateinit var viewBiniding:ActivityHomeBinding
    var tasksListFragementRef:TasksListFragement?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        viewBiniding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBiniding.root)

        viewBiniding.bottomNavagition
            .setOnItemSelectedListener {
                when(it.itemId){
                    R.id.navigation_tasks_list->{
                        tasksListFragementRef = TasksListFragement()
                        showFragement(tasksListFragementRef!!)
                    }
                    R.id.navigation_tasks_settings->{
                        showFragement(SettingsFragement())
                    }
                }
                return@setOnItemSelectedListener true
            }
        viewBiniding.addTask
            .setOnClickListener {
                showAddTaskBottomSheet()
            }
        viewBiniding.bottomNavagition.selectedItemId=R.id.navigation_tasks_list
    }

    private fun showAddTaskBottomSheet() {
        val addtaskSheet=AddTaskFragement()
        addtaskSheet.onTaskAddListener= AddTaskFragement.OnTaskAddListener {
            Snackbar.make(viewBiniding.root,"Task Added Successfuly",Snackbar.LENGTH_LONG).show()
            //notify tasks list fragement
            tasksListFragementRef?.loadTasks()
        }
        addtaskSheet.show(supportFragmentManager,"")

    }

    private fun showFragement(fragement: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragement_container,fragement)
            .setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
            .commit()

    }
}