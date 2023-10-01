package com.turanapps.medicationreminder.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "syrup")
data class Syrup(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "syrup_name") val syrupName: String
)