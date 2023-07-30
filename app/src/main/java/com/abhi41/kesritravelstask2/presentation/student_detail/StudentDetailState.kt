package com.abhi41.kesritravelstask2.presentation.student_detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.abhi41.kesritravelstask2.local.Subject

data class StudentDetailState(
    var name: String = "",
    var course: String = "CSE",
    var subject:MutableState<String> = mutableStateOf(String()),
    var marks:MutableState<Int> = mutableStateOf(0),
)
