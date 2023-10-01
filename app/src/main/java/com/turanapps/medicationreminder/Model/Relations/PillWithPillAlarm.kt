package com.turanapps.medicationreminder.Model.Relations

import androidx.room.Embedded
import androidx.room.Relation
import com.turanapps.medicationreminder.Model.Pill
import com.turanapps.medicationreminder.Model.PillAlarm

data class PillWithPillAlarm (
    @Embedded val pill: Pill,
    @Relation(
        parentColumn = "id",
        entityColumn = "pill_id"
    )
    val pillAlarms: List<PillAlarm>
)