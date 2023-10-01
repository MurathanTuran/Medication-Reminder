package com.turanapps.medicationreminder.Model.Relations

import androidx.room.Embedded
import androidx.room.Relation
import com.turanapps.medicationreminder.Model.Syrup
import com.turanapps.medicationreminder.Model.SyrupAlarm

data class SyrupWithSyrupAlarm (
    @Embedded val syrup: Syrup,
    @Relation(
        parentColumn = "id",
        entityColumn = "syrup_id"
    )
    val syrupAlarms: List<SyrupAlarm>
)