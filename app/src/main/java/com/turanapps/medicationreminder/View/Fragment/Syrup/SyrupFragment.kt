package com.turanapps.medicationreminder.View.Fragment.Syrup

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.turanapps.medicationreminder.Adapter.RecyclerAdapterSyrupFragment
import com.turanapps.medicationreminder.Error.ObserveErrors
import com.turanapps.medicationreminder.Model.SyrupAlarm
import com.turanapps.medicationreminder.Util.AlarmReceiver
import com.turanapps.medicationreminder.ViewModel.Fragment.Syrup.SyrupViewModel
import com.turanapps.medicationreminder.databinding.FragmentSyrupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SyrupFragment : Fragment() {

    private lateinit var binding: FragmentSyrupBinding

    private val viewModel: SyrupViewModel by viewModels()

    private lateinit var syrupAdapter: RecyclerAdapterSyrupFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSyrupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ObserveErrors.observe(viewLifecycleOwner, requireContext(),
            viewModel.getAllSyrupsWithAlarmsErrorEntity, viewModel.deleteSyrupWithAlarmsErrorEntity)

        clicked()

        syrupAdapter = RecyclerAdapterSyrupFragment(viewModel.getAllSyrupsWithAlarms() ?: listOf())
        binding.recyclerViewSyrupFragment.adapter = syrupAdapter
        binding.recyclerViewSyrupFragment.layoutManager = LinearLayoutManager(requireContext().applicationContext)

        observeForDelete()

    }

    private fun clicked(){
        fabClicked()
    }

    private fun fabClicked(){
        binding.fab.setOnClickListener {
            loadFragment(SyrupFragmentDirections.actionSyrupFragmentToSyrupDetailsFragment())
        }
    }

    private fun loadFragment(navDirections: NavDirections){
        Navigation.findNavController(requireView()).navigate(navDirections)
    }

    private fun observeForDelete(){

        syrupAdapter.deleteSyrup.observe(viewLifecycleOwner, Observer {
            if(it != null){
                cancelAllAlarms(it.syrupAlarms)
                viewModel.deleteSyrupWithAlarms(it.syrup)
                syrupAdapter.updateSyrups(viewModel.getAllSyrupsWithAlarms() ?: listOf())
                syrupAdapter.deleteSyrup.value = null
            }
        })

    }

    private fun cancelAllAlarms(alarms: List<SyrupAlarm>){
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