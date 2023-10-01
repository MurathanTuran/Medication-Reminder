package com.turanapps.medicationreminder.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.turanapps.medicationreminder.CONST.Const.ROOM_DATABASE_NAME
import com.turanapps.medicationreminder.Dao.PillDao
import com.turanapps.medicationreminder.Dao.SyrupDao
import com.turanapps.medicationreminder.Model.Pill
import com.turanapps.medicationreminder.Model.PillAlarm
import com.turanapps.medicationreminder.Model.Syrup
import com.turanapps.medicationreminder.Model.SyrupAlarm

@Database(entities = [Syrup::class, SyrupAlarm::class, Pill::class, PillAlarm::class], version = 1, exportSchema = false)
abstract class MedicationReminderDatabase: RoomDatabase() {

    abstract fun syrupDao(): SyrupDao
    abstract fun pillDao(): PillDao

    companion object {

        @Volatile
        private var instance: MedicationReminderDatabase? = null

        fun getInstance(context: Context): MedicationReminderDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): MedicationReminderDatabase {
            return Room.databaseBuilder(context.applicationContext, MedicationReminderDatabase::class.java, ROOM_DATABASE_NAME)
                .build()
        }

    }

}