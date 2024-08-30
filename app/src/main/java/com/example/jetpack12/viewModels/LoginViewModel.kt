package com.example.jetpack12.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack12.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val auth:FirebaseAuth = Firebase.auth
    var showAlert by mutableStateOf(false)

    fun login(email:String,password:String,onSuccess:()->Unit){
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    task->
                    if(task.isSuccessful){
                        onSuccess()
                    }else{
                        Log.d("Error en Firebase","Usuario y contraseña incorrectos.")
                        showAlert = true
                    }
                }
            }catch (e:Exception){
                Log.d("Error en JetPack","Error : ${e.localizedMessage}")
            }
        }
    }

    fun createUser(email: String,password: String,username:String,onSuccess:()-> Unit){
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    task->
                    if(task.isSuccessful){
                        saveUser(username)
                        onSuccess()
                    }else{
                        Log.d("Error en firebase","No se Pudo Crear el Usuario")
                        showAlert = true
                    }
                }
            }catch (e:Exception){
                Log.d("Error en JetPack","Error : ${e.localizedMessage}")
            }

        }
    }

    private fun saveUser(username:String){
        viewModelScope.launch (Dispatchers.IO){
            val id = auth.currentUser?.uid
            val email = auth.currentUser?.email
            val user = UserModel(userId = id.toString(), email = email.toString(), username = username)

            FirebaseFirestore.getInstance().collection("Users").add(user).addOnSuccessListener {
                Log.d("Guardado","Guardo Correctamente.")
            }.addOnFailureListener {
                Log.d("Error al guardar","Error al guadar en FireStore.")
            }
        }
    }

    fun closeAlert(){
        showAlert = false
    }

}