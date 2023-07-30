@file:OptIn(ExperimentalMaterial3Api::class)

package com.abhi41.kesritravelstask2.presentation.student_detail

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.abhi41.kesritravelstask2.local.Subject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.NumberFormatException

private const val TAG = "StudentDetailScreen"


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StudentDetailScreen(
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Student Information") }
            )
        }
    ) {
        val context = LocalContext.current
        val viewModel = hiltViewModel<StudentDetailViewModel>()
        val studentState = viewModel.studentState.value
        val listOfCourse = viewModel.listOfCourse
        val scope = rememberCoroutineScope()
        val selectedCourse = remember {
            mutableStateOf("CSE")
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding(), start = 10.dp, end = 10.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NameEditableTexts { name ->
                studentState.name = name
            }
            Spacer(modifier = Modifier.height(10.dp))
            CoursesDropDown(
                onSelectedCourse = { newValue ->
                    selectedCourse.value = newValue
                    studentState.course = selectedCourse.value
                },
                listOfCourse
            )
            Spacer(modifier = Modifier.height(10.dp))
            SubjectList(selectedCourse.value, listOfCourse)
            Spacer(modifier = Modifier.height(20.dp))
            SaveDetails(onClick = {
                if (!studentState.name.equals("")) {
                    scope.launch(Dispatchers.IO) {
                        viewModel.insertStudentInfo()
                        onNavigateBack()
                    }
                } else {
                    Toast.makeText(context, "Student Name Should not empty", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
    }
}

@Composable
fun NameEditableTexts(
    studentName: (String) -> Unit
) {
    var name by remember {
        mutableStateOf("")
    }

    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = name,
        onValueChange = {
            name = it
            studentName(name)
        },
        label = {
            Text(text = "Student Name", color = Color.Black)
        },
        singleLine = true
    )
}

@Composable
fun CoursesDropDown(
    onSelectedCourse: (String) -> Unit,
    listOfCourse: HashMap<String, List<Subject>>
) {
    var expanded by remember { mutableStateOf(false) }
    var texFilledSize by remember {
        mutableStateOf(Size.Zero)
    }
    var selectedItem by remember { mutableStateOf("CSE") }

    val icon = if (expanded) {
        Icons.Default.KeyboardArrowUp
    } else {
        Icons.Default.KeyboardArrowDown
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedItem,
            enabled = false,
            textStyle = TextStyle(color = Color.Black),
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned {
                    texFilledSize = it.size.toSize()
                },
            label = { Text(text = "Select Course") },
            trailingIcon = {
                Icon(
                    imageVector = icon, contentDescription = "",
                    modifier = Modifier.clickable {
                        expanded = !expanded
                    }
                )
            }
        )
        //Text(text = selectedItem)
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            listOfCourse.forEach { course ->
                DropdownMenuItem(
                    onClick = {
                        selectedItem = course.key
                        onSelectedCourse(selectedItem)
                        expanded = false
                    },
                    text = {
                        Text(text = course.key, color = Color.Black)
                    }
                )
            }
        }
    }
}


@Composable
fun SubjectList(
    selectedCourse: String,
    listOfCourse: HashMap<String, List<Subject>>
) {
    val viewModel = hiltViewModel<StudentDetailViewModel>()
    var mutableStudentList = viewModel.mutableStudentList
    mutableStudentList.clear()

    listOfCourse.getValue(selectedCourse).map {
        mutableStudentList.add(
            StudentDetailState(
                subject = mutableStateOf(it.subject),
                marks = mutableStateOf(it.marks)
            )
        )
    }

    LazyColumn {
        items(mutableStudentList) { item ->
            ListSubject(item)
        }
    }

}

@SuppressLint("UnrememberedMutableState")
@Composable
fun ListSubject(item: StudentDetailState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(text = item.subject.value, style = LocalTextStyle.current, color = Color.Black)
        Spacer(modifier = Modifier.width(6.dp))
        TextField(
            value = item.marks.value.toString(),
            onValueChange = {
                try {
                    if (it.toInt() <= 100) {
                        item.marks.value = it.toInt()
                    }
                } catch (e: NumberFormatException) {
                    Log.e(TAG, e.printStackTrace().toString())
                }
            },
            label = { Text(text = "marks") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

@Composable
fun SaveDetails(onClick: () -> Unit) {

    Button(onClick = {
        onClick()
    }, shape = RoundedCornerShape(40)) {
        Text(
            text = "Save",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = TextStyle(fontSize = 15.sp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StudentDetailScreenPreview() {
    StudentDetailScreen(onNavigateBack = {})
}