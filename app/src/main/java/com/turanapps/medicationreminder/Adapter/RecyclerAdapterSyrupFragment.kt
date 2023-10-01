package com.turanapps.medicationreminder.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.turanapps.medicationreminder.Model.Relations.SyrupWithSyrupAlarm
import com.turanapps.medicationreminder.databinding.SyrupRecyclerRowBinding
import java.sql.Time

class RecyclerAdapterSyrupFragment(private var syrups: List<SyrupWithSyrupAlarm>): RecyclerView.Adapter<RecyclerAdapterSyrupFragment.ViewHolder>() {

    var deleteSyrup = MutableLiveData<SyrupWithSyrupAlarm?>(null)

    class ViewHolder(val binding: SyrupRecyclerRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SyrupRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.apply {

            val timeTextArray = arrayOf(firstTimeText, secondTimeText, thirdTimeText, fourthTimeText, fifthTimeText)

            syrupNameText.text = syrups.get(position).syrup.syrupName

            val syrupAlarms = syrups.get(position).syrupAlarms

            for(i in 0 until syrupAlarms.size){
                timeTextArray.get(i).apply {
                    visibility = View.VISIBLE
                    val time = Time(syrupAlarms.get(i).alarmTime)
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
                holder.binding.timesLinearLayout.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context, com.turanapps.medicationreminder.R.anim.times_linear_layout_animation))
            }
        }

        holder.binding.deleteImageView.setOnClickListener {
            deleteSyrup.value = syrups.get(position)
        }

    }

    override fun getItemCount(): Int {
        return syrups.size
    }

    fun updateSyrups(newSyrups: List<SyrupWithSyrupAlarm>) {
        syrups = newSyrups
        notifyDataSetChanged()
    }

}