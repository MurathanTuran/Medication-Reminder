package com.turanapps.medicationreminder.ViewModel.Fragment.Syrup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.turanapps.medicationreminder.Error.Error
import com.turanapps.medicationreminder.Dao.SyrupDao
import com.turanapps.medicationreminder.Model.Syrup
import com.turanapps.medicationreminder.Model.SyrupAlarm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SyrupDetailsViewModel @Inject constructor(
    private val syrupDao: SyrupDao
) : ViewModel() {

    private var insertSyrupErrorB = MutableLiveData<Boolean?>()
    private var insertSyrupError = "Şurup eklerken hata ile karşılaşıldı"
    val insertSyrupErrorEntity = Error(insertSyrupErrorB, insertSyrupError)

    private var insertSyrupAlarmErrorB = MutableLiveData<Boolean?>()
    private var insertSyrupAlarmError = "Şurup alarm eklerken hata ile karşılaşıldı"
    val insertSyrupAlarmErrorEntity = Error(insertSyrupAlarmErrorB, insertSyrupAlarmError)

    private var _readyToGoSyrupFragmentB = MutableLiveData<Boolean>(false)
    val readyToGoSyrupFragmentB: LiveData<Boolean> = _readyToGoSyrupFragmentB

    fun insertSyrup(syrup: Syrup): Int? {
        return try {
            runBlocking {
                _readyToGoSyrupFragmentB.value = true
                return@runBlocking syrupDao.insertSyrup(syrup).toInt()
            }
        } catch (e: Exception) {
            println(e.localizedMessage)
            insertSyrupErrorB.value = true
            return null
        }
    }

    fun insertSyrupAlarm(syrupAlarm: SyrupAlarm): Int? {
        return try {
            runBlocking {
                return@runBlocking syrupDao.insertSyrupAlarm(syrupAlarm).toInt()
            }
        } catch (e: Exception) {
            println(e.localizedMessage)
            insertSyrupAlarmErrorB.value = true
            return null
        }
    }

}