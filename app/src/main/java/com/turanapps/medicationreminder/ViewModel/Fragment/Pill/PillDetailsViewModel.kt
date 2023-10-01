package com.turanapps.medicationreminder.ViewModel.Fragment.Pill

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.turanapps.medicationreminder.Dao.PillDao
import com.turanapps.medicationreminder.Error.Error
import com.turanapps.medicationreminder.Model.Pill
import com.turanapps.medicationreminder.Model.PillAlarm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PillDetailsViewModel @Inject constructor(
    private val pillDao: PillDao
): ViewModel() {

    private var insertPillErrorB = MutableLiveData<Boolean?>()
    private var insertPillError = "Hap eklerken hata ile karşılaşıldı"
    val insertPillErrorEntity = Error(insertPillErrorB, insertPillError)

    private var insertPillAlarmErrorB = MutableLiveData<Boolean?>()
    private var insertPillAlarmError = "Hap alarm eklerken hata ile karşılaşıldı"
    val insertPillAlarmErrorEntity = Error(insertPillAlarmErrorB, insertPillAlarmError)

    private var _readyToGoPillFragmentB = MutableLiveData<Boolean>(false)
    val readyToGoPillFragmentB: LiveData<Boolean> = _readyToGoPillFragmentB

    fun insertPill(pill: Pill): Int? {
        return try {
            runBlocking {
                _readyToGoPillFragmentB.value = true
                return@runBlocking pillDao.insertPill(pill).toInt()
            }
        } catch (e: Exception) {
            println(e.localizedMessage)
            insertPillErrorB.value = true
            return null
        }
    }

    fun insertPillAlarm(pillAlarm: PillAlarm): Int? {
        return try {
            runBlocking {
                return@runBlocking pillDao.insertPillAlarm(pillAlarm).toInt()
            }
        } catch (e: Exception) {
            println(e.localizedMessage)
            insertPillAlarmErrorB.value = true
            return null
        }
    }

}