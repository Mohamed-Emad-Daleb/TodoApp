package com.route.todoapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.route.todoapp.dao.TasksDao

@Database(entities = [Task::class], version = 1, exportSchema = true)
abstract class MyDataBase:RoomDatabase() {
    abstract fun tasksDao():TasksDao
    companion object{
        //static
        private var instance: MyDataBase?=null
        fun getInstance(context: Context): MyDataBase {
            if(instance ==null){
                //initialize
                instance =Room.databaseBuilder(context.applicationContext,
                    MyDataBase::class.java,
                    "tasksDB")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return  instance!!
        }
    }
}