package com.turanapps.medicationreminder.View.Fragment.Pill

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.turanapps.medicationreminder.Adapter.RecyclerAdapterPillFragment
import com.turanapps.medicationreminder.Error.ObserveErrors
import com.turanapps.medicationreminder.Model.PillAlarm
import com.turanapps.medicationreminder.Util.AlarmReceiver
import com.turanapps.medicationreminder.ViewModel.Fragment.Pill.PillViewModel
import com.turanapps.medicationreminder.databinding.FragmentPillBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PillFragment : Fragment() {

    private lateinit var binding: FragmentPillBinding

    private val viewModel: PillViewModel by viewModels()

    private lateinit var pillAdapter: RecyclerAdapterPillFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPillBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ObserveErrors.observe(viewLifecycleOwner, requireContext(),
            viewModel.getAllPillsWithAlarmsErrorEntity, viewModel.deletePillWithAlarmsErrorEntity)

        clicked()

        pillAdapter = RecyclerAdapterPillFragment(viewModel.getAllPillsWithAlarms() ?: listOf())
        binding.recyclerViewPillFragment.adapter = pillAdapter
        binding.recyclerViewPillFragment.layoutManager = LinearLayoutManager(requireContext().applicationContext)

        observeForDelete()

    }

    private fun clicked(){
        fabClicked()
    }

    private fun fabClicked(){
        binding.fab.setOnClickListener {
            loadFragment(PillFragmentDirections.actionPillFragmentToPillDetailsFragment())
        }
    }

    private fun loadFragment(navDirections: NavDirections){
        Navigation.findNavController(requireView()).navigate(navDirections)
    }

    private fun observeForDelete(){

        pillAdapter.deletePill.observe(viewLifecycleOwner, Observer {
            if(it != null){
                cancelAllAlarms(it.pillAlarms)
                viewModel.deletePillWithAlarms(it.pill)
                pillAdapter.updatePills(viewModel.getAllPillsWithAlarms() ?: listOf())
                pillAdapter.deletePill.value = null
            }
        })

    }

    private fun cancelAllAlarms(alarms: List<PillAlarm>){
        for(alarm in alarms){
            cancelAlarm(alarm.id.toInt())
        }
    }

    private fun cancelAlarm(alarmId: Int){
        val notificationManager = NotificationManagerCompat.from(requireContext())
        notificationManager.cancel(alarmId)

        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), alarmId, intent, PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE)
        pendingIntent?.cancel()
    }

}