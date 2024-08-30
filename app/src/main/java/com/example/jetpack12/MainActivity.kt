package com.example.jetpack12

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpack12.navigation.NavManager
import com.example.jetpack12.ui.theme.JetPack12Theme
import com.example.jetpack12.viewModels.LoginViewModel
import com.example.jetpack12.viewModels.NotesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetPack12Theme {
                val loginVM:LoginViewModel by viewModels()
                val notesVM:NotesViewModel by viewModels()
                Surface(modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)) {
                    NavManager(loginVM = loginVM, notesVM = notesVM)
                }
            }
        }
    }
}

