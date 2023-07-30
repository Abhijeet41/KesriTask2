package com.abhi41.kesritravelstask2.navigation

sealed class Screen(
    val route: String
){
    object Students: Screen("Student_Screen")
    object StudentDetail: Screen("Student_Detail_Screen")
}
