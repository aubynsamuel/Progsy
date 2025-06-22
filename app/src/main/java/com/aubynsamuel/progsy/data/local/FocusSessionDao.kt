package com.aubynsamuel.progsy.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aubynsamuel.progsy.domain.model.FocusSession
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate


@Dao
interface FocusSessionDao {
    @Query("SELECT * FROM focus_sessions ORDER BY startTime DESC")
    fun getAllSessions(): Flow<List<FocusSession>>

    @Query("SELECT * FROM focus_sessions WHERE id = :id")
    suspend fun getSessionById(id: String): FocusSession?

    @Insert
    suspend fun insertSession(session: FocusSession)

    @Update
    suspend fun updateSession(session: FocusSession)

    @Query("SELECT * FROM focus_sessions WHERE DATE(startTime) >= :startDate ORDER BY startTime DESC")
    suspend fun getSessionsFromDate(startDate: LocalDate): List<FocusSession>
}