package com.turanapps.medicationreminder.ViewModel.Fragment.Syrup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.turanapps.medicationreminder.Dao.SyrupDao
import com.turanapps.medicationreminder.Error.Error
import com.turanapps.medicationreminder.Model.Relations.SyrupWithSyrupAlarm
import com.turanapps.medicationreminder.Model.Syrup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SyrupViewModel @Inject constructor(
    private val syrupDao: SyrupDao
) : ViewModel() {

    private var deleteSyrupWithAlarmsErrorB = MutableLiveData<Boolean?>()
    private var deleteSyrupWithAlarmsError = "Şurup silinirken hata ile karşılaşıldı"
    val deleteSyrupWithAlarmsErrorEntity = Error(deleteSyrupWithAlarmsErrorB, deleteSyrupWithAlarmsError)

    private var getAllSyrupsWithAlarmsErrorB = MutableLiveData<Boolean?>()
    private var getAllSyrupsWithAlarmsError = "Şuruplar veri tabanından çekilirken hata ile karşılaşıldı"
    val getAllSyrupsWithAlarmsErrorEntity = Error(getAllSyrupsWithAlarmsErrorB, getAllSyrupsWithAlarmsError)

    fun deleteSyrupWithAlarms(syrup: Syrup){
        try {
            runBlocking {
                withContext(Dispatchers.IO) {
                    syrupDao.deleteSyrup(syrup)
                    syrupDao.deleteAlarms(syrup.id)
                }
            }
        }catch (e: Exception){
            println(e.localizedMessage)
            deleteSyrupWithAlarmsErrorB.value = true
        }
    }

    fun getAllSyrupsWithAlarms(): List<SyrupWithSyrupAlarm>? {
        return try {
             runBlocking {
                 withContext(Dispatchers.IO){
                     return@withContext syrupDao.getAllSyrupsWithAlarms()
                 }
            }
        }catch (e: Exception){
            println(e.localizedMessage)
            getAllSyrupsWithAlarmsErrorB.value = true
            return null
        }
    }

}