@file:OptIn(ExperimentalMaterial3Api::class)

package com.abhi41.kesritravelstask2.presentation.students

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abhi41.kesritravelstask2.local.StudentEntity
import com.abhi41.kesritravelstask2.local.Subject
import com.abhi41.kesritravelstask2.presentation.student_detail.StudentDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StudentInfoScreen(
    onNavigationListener: () -> Unit
) {
    val viewModel: StudentInfoViewModel = hiltViewModel()
    val listOfStudents = viewModel.readStudentList.value.studentList

    Scaffold(
        floatingActionButton = { FloatingButton(onNavigationListener) }
    ) {
        StudentList(
            studentList = listOfStudents
        )
    }
}

@Composable
fun StudentList(studentList: List<StudentEntity>) {
    LazyColumn {
        items(studentList) { data ->
            SingleStudentInfo(data)
        }
    }
}

@Composable
fun SingleStudentInfo(data: StudentEntity) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)) {
                    append("Name : ")
                }
                append(data.name)
            })
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)) {
                    append("Course : ")
                }
                append(data.courses)
            })
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.wrapContentWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Column(horizontalAlignment = Alignment.Start){
                    Text(text = "Subjects", fontWeight = FontWeight.Bold, color = Color.Black)
                    data.subjects.forEach { subject: Subject ->
                        Text(text = subject.subject)
                    }
                }
                Spacer(modifier = Modifier.width(6.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Marks", fontWeight = FontWeight.Bold, color = Color.Black)
                    data.subjects.forEach { subject: Subject ->
                        Text(text = subject.marks.toString())
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)) {
                    append("Total : ")
                }
                append(data.total.toString())
            })
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)) {
                    append("Average : ")
                }
                append(data.averageNumber.toString())
            })
        }
    }
}

@Composable
fun FloatingButton(
    onNavigationListener: () -> Unit
) {
    FloatingActionButton(
        onClick = { onNavigationListener() }
    ) {
        Icon(Icons.Default.Add, contentDescription = "add")
    }
}

@Preview(showBackground = true)
@Composable
fun StudentInfoScreenPreview() {
    SingleStudentInfo(
        StudentEntity(
            id = 1,
            name = "Abhijeet",
            averageNumber = 65,
            courses = "CSE",
            subjects = listOf(
                Subject(subject = "c++", marks = 55),
                Subject(subject = "Java", marks = 65),
                Subject(subject = "Android", marks = 85),
            ),
            total = 10
        )
    )
}