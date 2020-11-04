package com.tam.workoutapp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_status.view.*

class ExerciseStatus(val items : ArrayList<ExerciseModel>,val context : Context) : RecyclerView.Adapter<ExerciseStatus.ViewHolder>() {


             class ViewHolder(view : View) : RecyclerView.ViewHolder(view){

                 val tvItem = view.tvR

             }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_status,parent,false))
    }

    override fun getItemCount(): Int {
     return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model : ExerciseModel = items[position]

        holder.tvItem.text = model.getId().toString()

        if(model.getIsSelected()){
            holder.tvItem.background = ContextCompat.getDrawable(context,R.drawable.circular_color)
            holder.tvItem.setTextColor(Color.parseColor("#212121"))
        } else if(model.getIsCompleted()){
            holder.tvItem.background = ContextCompat.getDrawable(context,R.drawable.item_circular_color_animation)
            holder.tvItem.setTextColor(Color.parseColor("#212121"))
        } else{
            holder.tvItem.background = ContextCompat.getDrawable(context,R.drawable.item_circular_color_accent_border)
            holder.tvItem.setTextColor(Color.parseColor("#212121"))
        }


    }


}