package com.aubynsamuel.progsy.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aubynsamuel.progsy.domain.model.DailyCheckIn
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate


@Dao
interface DailyCheckInDao {
    @Query("SELECT * FROM daily_checkins ORDER BY date DESC")
    fun getAllCheckIns(): Flow<List<DailyCheckIn>>

    @Query("SELECT * FROM daily_checkins WHERE date = :date")
    suspend fun getCheckInByDate(date: LocalDate): DailyCheckIn?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCheckIn(checkIn: DailyCheckIn)

    @Update
    suspend fun updateCheckIn(checkIn: DailyCheckIn)

    @Query("SELECT * FROM daily_checkins WHERE date >= :startDate ORDER BY date DESC LIMIT 7")
    suspend fun getWeeklyCheckIns(startDate: LocalDate): List<DailyCheckIn>
}
