package com.abhi41.kesritravelstask2.presentation.student_detail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.abhi41.kesritravelstask2.local.StudentDao
import com.abhi41.kesritravelstask2.local.StudentEntity
import com.abhi41.kesritravelstask2.local.Subject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "StudentDetailViewModel"
@HiltViewModel
class StudentDetailViewModel @Inject constructor(
    private val studentDao: StudentDao
) : ViewModel() {

    var mutableStudentList = mutableListOf(StudentDetailState())

    private val _studentState = mutableStateOf(StudentDetailState())
    val studentState: State<StudentDetailState> = _studentState

    val listOfCourse: HashMap<String, List<Subject>> = hashMapOf(
        "CSE" to listOf(Subject("C"), Subject("Java"), Subject("DataStructure")),
        "ECE" to listOf(
            Subject("Digital electronics"),
            Subject("maths"),
            Subject("Microprocessor")
        ),
        "Mech" to listOf(
            Subject("Nanotechnology"),
            Subject("Biometrics"),
            Subject("Acoustics")
        ),
        "Civil" to listOf(
            Subject("Material science"),
            Subject("Construction Engineering"),
            Subject("Hydraulic science")
        )
    )

    suspend fun insertStudentInfo() {

        var listOfSubjects: MutableList<Subject> = mutableListOf()
        var totalNumber = 0
        var averageNumber = 0

        mutableStudentList.map {
            listOfSubjects.add(Subject(subject = it.subject.value, marks = it.marks.value))
            totalNumber = totalNumber + it.marks.value
            averageNumber = averageNumber + it.marks.value
        }
        averageNumber = averageNumber / 3

        val studentEntity = StudentEntity(
            name = _studentState.value.name,
            courses = _studentState.value.course,
            subjects = listOfSubjects,
            total = totalNumber,
            averageNumber = averageNumber
        )
        Log.d(TAG, studentEntity.toString())
        studentDao.insertStudent(studentEntity = studentEntity)
    }

}