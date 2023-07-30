package com.abhi41.kesritravelstask2.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.abhi41.kesritravelstask2.util.Converters

@Database(
    entities = [StudentEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class StudentDatabase : RoomDatabase() {
    abstract val dao: StudentDao
}