package com.turanapps.medicationreminder.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pill_alarm")
data class PillAlarm(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "pill_id") val pillId: Long,
    @ColumnInfo(name = "alarm_time") val alarmTime: Long
)