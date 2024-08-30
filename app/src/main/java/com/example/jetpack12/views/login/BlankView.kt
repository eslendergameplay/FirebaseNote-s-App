package com.example.jetpack12.views.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun BlankView(navC:NavController){
    LaunchedEffect(Unit) {
        if(!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
            navC.navigate("Home")
        }else{
            navC.navigate("Login")
        }
    }
}