package com.turanapps.medicationreminder.ViewModel.Fragment.Pill

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.turanapps.medicationreminder.Dao.PillDao
import com.turanapps.medicationreminder.Error.Error
import com.turanapps.medicationreminder.Model.Pill
import com.turanapps.medicationreminder.Model.Relations.PillWithPillAlarm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PillViewModel @Inject constructor(
    private val pillDao: PillDao
) : ViewModel() {

    private var deletePillWithAlarmsErrorB = MutableLiveData<Boolean?>()
    private var deletePillWithAlarmsError = "Hap silinirken hata ile karşılaşıldı"
    val deletePillWithAlarmsErrorEntity = Error(deletePillWithAlarmsErrorB, deletePillWithAlarmsError)

    private var getAllPillsWithAlarmsErrorB = MutableLiveData<Boolean?>()
    private var getAllPillsWithAlarmsError = "Haplar veri tabanından çekilirken hata ile karşılaşıldı"
    val getAllPillsWithAlarmsErrorEntity = Error(getAllPillsWithAlarmsErrorB, getAllPillsWithAlarmsError)

    fun deletePillWithAlarms(pill: Pill){
        try {
            runBlocking {
                withContext(Dispatchers.IO) {
                    pillDao.deletePill(pill)
                    pillDao.deleteAlarms(pill.id)
                }
            }
        }catch (e: Exception){
            println(e.localizedMessage)
            deletePillWithAlarmsErrorB.value = true
        }
    }

    fun getAllPillsWithAlarms(): List<PillWithPillAlarm>? {
        return try {
            runBlocking {
                withContext(Dispatchers.IO){
                    return@withContext pillDao.getAllPillsWithAlarms()
                }
            }
        }catch (e: Exception){
            println(e.localizedMessage)
            getAllPillsWithAlarmsErrorB.value = true
            return null
        }
    }

}