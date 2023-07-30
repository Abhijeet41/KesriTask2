package com.abhi41.kesritravelstask2.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhi41.kesritravelstask2.util.UtilConstants

@Entity(tableName = UtilConstants.TABLE_STUDENT)
data class StudentEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val name: String,
    val courses: String,
    val subjects: List<Subject>,
    val total: Int,
    val averageNumber:Int
)
data class Subject(
    var subject: String,
    var marks: Int = 0
)
