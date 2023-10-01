package com.turanapps.medicationreminder.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.turanapps.medicationreminder.Model.Relations.SyrupWithSyrupAlarm
import com.turanapps.medicationreminder.Model.Syrup
import com.turanapps.medicationreminder.Model.SyrupAlarm

@Dao
interface SyrupDao {

    @Query("SELECT * FROM syrup")
    suspend fun getAllSyrups(): List<Syrup>

    @Insert
    suspend fun insertSyrup(syrup: Syrup): Long

    @Insert
    suspend fun insertSyrupAlarm(syrupAlarm: SyrupAlarm): Long

    @Delete
    suspend fun deleteSyrup(syrup: Syrup)

    @Transaction
    @Query("SELECT * FROM syrup WHERE id = :id")
    fun getSyrupWithAlarms(id: Long): List<SyrupWithSyrupAlarm>

    @Transaction
    @Query("SELECT * FROM syrup")
    fun getAllSyrupsWithAlarms(): List<SyrupWithSyrupAlarm>

    @Transaction
    @Query("DELETE FROM syrup_alarm WHERE syrup_id = :syrupId")
    fun deleteAlarms(syrupId: Long)

}