package com.example.jetpack12.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetpack12.viewModels.LoginViewModel
import com.example.jetpack12.viewModels.NotesViewModel
import com.example.jetpack12.views.login.BlankView
import com.example.jetpack12.views.login.Tabsview
import com.example.jetpack12.views.notes.AddNotesView
import com.example.jetpack12.views.notes.EditNoteView
import com.example.jetpack12.views.notes.HomeView
import com.example.jetpack12.views.notes.PhotoView

@Composable
fun NavManager(loginVM:LoginViewModel,notesVM:NotesViewModel){
    val navC = rememberNavController()
    NavHost(navController = navC, startDestination = "Blank"){
        composable("Blank"){
            BlankView(navC)
        }
        composable("Login"){
            Tabsview(navC = navC, loginVM = loginVM)
        }
        composable("Home"){
            HomeView(navC = navC, notesVM = notesVM )
        }
        composable("AddNoteView"){
            AddNotesView(navC,notesVM)
        }
        composable("EditNoteView/{idDoc}", arguments = listOf(navArgument("idDoc"){type = NavType.StringType})){
            val idDoc = it.arguments?.getString("idDoc")
            EditNoteView(navC,notesVM,idDoc!!)
        }
        composable("PhotoView/{idDoc}", arguments = listOf(navArgument("idDoc"){type =NavType.StringType})){
            val idDoc = it.arguments?.getString("idDoc")
            PhotoView(notesVM = notesVM, idDoc = idDoc!!)
        }
    }
}