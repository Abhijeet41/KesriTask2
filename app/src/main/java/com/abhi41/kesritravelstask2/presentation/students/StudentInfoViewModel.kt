package com.abhi41.kesritravelstask2.presentation.students

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhi41.kesritravelstask2.local.StudentDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentInfoViewModel @Inject constructor(
    private val dao: StudentDao
) : ViewModel(){

    private val _readStudentList = mutableStateOf(StudentInfoState())
    val readStudentList: State<StudentInfoState> = _readStudentList

     init {
         viewModelScope.launch (Dispatchers.IO){
             dao.readStudentsData().onEach {
                 _readStudentList.value = _readStudentList.value.copy(it)
             }.launchIn(this)
         }
     }

}