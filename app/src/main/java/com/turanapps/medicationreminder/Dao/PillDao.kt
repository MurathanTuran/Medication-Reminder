package com.turanapps.medicationreminder.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.turanapps.medicationreminder.Model.Pill
import com.turanapps.medicationreminder.Model.PillAlarm
import com.turanapps.medicationreminder.Model.Relations.PillWithPillAlarm

@Dao
interface PillDao {

    @Query("SELECT * FROM pill")
    suspend fun getAllPills(): List<Pill>

    @Insert
    suspend fun insertPill(pill: Pill): Long

    @Insert
    suspend fun insertPillAlarm(pillAlarm: PillAlarm): Long

    @Delete
    suspend fun deletePill(pill: Pill)

    @Transaction
    @Query("SELECT * FROM pill WHERE id = :id")
    fun getPillWithAlarms(id: Long): List<PillWithPillAlarm>

    @Transaction
    @Query("SELECT * FROM pill")
    fun getAllPillsWithAlarms(): List<PillWithPillAlarm>

    @Transaction
    @Query("DELETE FROM pill_alarm WHERE pill_id = :pillId")
    fun deleteAlarms(pillId: Long)

}