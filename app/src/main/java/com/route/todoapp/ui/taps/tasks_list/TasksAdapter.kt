package com.route.todoapp.ui.taps.tasks_list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.route.todoapp.databinding.ItemTaskBinding
import com.route.todoapp.model.Task

class TasksAdapter(var tasks:MutableList<Task>?) :RecyclerView.Adapter<TasksAdapter.ViewHolder>(){
    var onItemClicked:OnItemClicked?=null
    var onItemDeleteClicked:OnItemDeleteClicked?=null
    class ViewHolder(val itemBiniding:ItemTaskBinding):RecyclerView.ViewHolder(itemBiniding.root){
        fun bind(task:Task){
            itemBiniding.title.text=task.title
            itemBiniding.description.text=task.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBiniding=ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false
        )
        return ViewHolder(itemBiniding)
    }

    override fun getItemCount(): Int = tasks?.size?:0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks!!.get(position))
        if(tasks!![position].isDone){
            holder.itemBiniding.isDone.setBackgroundColor(Color.GREEN)
        }
        if(onItemClicked!=null){
            holder.itemBiniding.card.setOnLongClickListener( View.OnLongClickListener {
                onItemClicked?.onItemClick(tasks!![position])
                true
            })
        }
        if(onItemDeleteClicked!=null){
            holder.itemBiniding.delete.setOnClickListener {
                holder.itemBiniding.swipe.close(true)
                onItemDeleteClicked?.onItemDeleteClick(position,tasks!![position])
            }
        }

    }

    fun bindTasks(tasks: MutableList<Task>) {
        this.tasks=tasks
        notifyDataSetChanged()
    }

    fun taskDelet(task: Task) {
        val position=tasks?.indexOf(task)
        tasks?.remove(task)
        notifyItemRemoved(position!!)
    }
}
fun interface OnItemClicked{
    fun onItemClick(task: Task)
}
fun interface OnItemDeleteClicked{
    fun onItemDeleteClick(position: Int,task: Task)
}