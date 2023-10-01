package com.turanapps.medicationreminder.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.turanapps.medicationreminder.Model.Relations.PillWithPillAlarm
import com.turanapps.medicationreminder.R
import com.turanapps.medicationreminder.databinding.PillRecyclerRowBinding
import java.sql.Time

class RecyclerAdapterPillFragment(private var pills: List<PillWithPillAlarm>): RecyclerView.Adapter<RecyclerAdapterPillFragment.ViewHolder>() {

    var deletePill = MutableLiveData<PillWithPillAlarm?>(null)

    class ViewHolder(val binding: PillRecyclerRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PillRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerAdapterPillFragment.ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.apply {

            val timeTextArray = arrayOf(firstTimeText, secondTimeText, thirdTimeText, fourthTimeText, fifthTimeText)

            pillNameText.text = pills.get(position).pill.pillName

            val pillAlarms = pills.get(position).pillAlarms

            for(i in 0 until pillAlarms.size){
                timeTextArray.get(i).apply {
                    visibility = View.VISIBLE
                    val time = Time(pillAlarms.get(i).alarmTime)
                    val hour = time.hours
                    val minute = time.minutes
                    val hourString = if(hour<10) "0${hour}" else "$hour"
                    val minuteString = if(minute<10) "0${minute}" else "$minute"
                    text = "${hourString}:${minuteString}"
                }
            }
        }

        holder.binding.clockImageView.setOnClickListener {
            if (holder.binding.timesLinearLayout.visibility == View.VISIBLE)
                holder.binding.timesLinearLayout.visibility = View.GONE
            else {
                holder.binding.timesLinearLayout.visibility = View.VISIBLE
                holder.binding.timesLinearLayout.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context, R.anim.times_linear_layout_animation))
            }
        }

        holder.binding.deleteImageView.setOnClickListener {
            deletePill.value = pills.get(position)
        }

    }

    override fun getItemCount(): Int {
        return pills.size
    }

    fun updatePills(newPills: List<PillWithPillAlarm>) {
        pills = newPills
        notifyDataSetChanged()
    }

}