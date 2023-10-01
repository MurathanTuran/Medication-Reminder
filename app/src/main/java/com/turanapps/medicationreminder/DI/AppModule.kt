package com.turanapps.medicationreminder.DI

import android.content.Context
import androidx.room.Room
import com.turanapps.medicationreminder.CONST.Const.ROOM_DATABASE_NAME
import com.turanapps.medicationreminder.Database.MedicationReminderDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectSyrupDao(
        database: MedicationReminderDatabase
    ) = database.syrupDao()

    @Singleton
    @Provides
    fun injectPillDao(
        database: MedicationReminderDatabase
    ) = database.pillDao()

    @Singleton
    @Provides
    fun injectMedicationReminderDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, MedicationReminderDatabase::class.java, ROOM_DATABASE_NAME).build()

}