package com.route.todoapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.route.todoapp.model.Task
@Dao
interface TasksDao {
    @Insert
    fun insertTask(task:Task)

    @Update
    fun apdateTask(task:Task)

    @Delete
    fun deleteTask(task:Task)

    @Query("select * from tasks")
    fun getAllTask():List<Task>

    @Query("select * from tasks where dateTime = :dataTime")
    fun getTaskByDay(dataTime:Long):List<Task>
}