package com.turanapps.medicationreminder.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "syrup_alarm")
data class SyrupAlarm(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "syrup_id") val syrupId: Long,
    @ColumnInfo(name = "alarm_time") val alarmTime: Long
)