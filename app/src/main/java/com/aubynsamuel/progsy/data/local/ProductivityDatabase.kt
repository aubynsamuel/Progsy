package com.aubynsamuel.progsy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aubynsamuel.progsy.domain.model.DailyCheckIn
import com.aubynsamuel.progsy.domain.model.FocusSession
import com.aubynsamuel.progsy.domain.model.Task

@Database(
    entities = [Task::class, DailyCheckIn::class, FocusSession::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ProductivityDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun dailyCheckInDao(): DailyCheckInDao
    abstract fun focusSessionDao(): FocusSessionDao
}