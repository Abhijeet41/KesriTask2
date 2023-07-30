package com.abhi41.kesritravelstask2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abhi41.kesritravelstask2.presentation.student_detail.StudentDetailScreen
import com.abhi41.kesritravelstask2.presentation.students.StudentInfoScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController =navController,
        startDestination = Screen.Students.route
    ) {
        composable(
            route = Screen.Students.route
        ){
            StudentInfoScreen(
                onNavigationListener = {
                    navController.navigate(Screen.StudentDetail.route)
                }
            )
        }

        composable(
            route = Screen.StudentDetail.route
        ){
            StudentDetailScreen()
        }
    }
}