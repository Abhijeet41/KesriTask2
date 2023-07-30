package com.abhi41.kesritravelstask2.presentation.students

import com.abhi41.kesritravelstask2.local.StudentEntity

data class StudentInfoState(
    val studentList: List<StudentEntity> = emptyList()
)