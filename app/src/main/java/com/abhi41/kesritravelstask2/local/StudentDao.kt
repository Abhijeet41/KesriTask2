package com.abhi41.kesritravelstask2.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(studentEntity: StudentEntity)

    @Query("SELECT * FROM student ORDER BY id ASC")
    fun readStudentsData(): Flow<List<StudentEntity>>
}