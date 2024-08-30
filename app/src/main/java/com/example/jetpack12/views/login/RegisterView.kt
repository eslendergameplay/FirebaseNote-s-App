package com.example.jetpack12.views.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetpack12.components.Alert
import com.example.jetpack12.viewModels.LoginViewModel

@Composable
fun RegisterView(navC:NavController,loginVM:LoginViewModel){
    Column (horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.fillMaxSize()){
        var email by remember {mutableStateOf("")}
        var password by remember { mutableStateOf("")}
        var username by remember { mutableStateOf("")}

        OutlinedTextField(value = username, onValueChange = {username = it},label = { Text(text = "Coloque su Username Aqui :")},modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp))
        OutlinedTextField(value = email, onValueChange = {email = it},label = { Text(text = "Coloque su Email Aqui :")}, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp))
        OutlinedTextField(value = password, onValueChange = {password = it},label = { Text(text = "Coloque la Contraseña Aqui :")}, visualTransformation = PasswordVisualTransformation(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp))
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { loginVM.createUser(email, password, username){
            navC.navigate("Home")
        } }, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)) {
            Text(text = "Registrarse")
        }

        if(loginVM.showAlert){
            Alert(title = "Alerta", message = "Usuario no Creado.", confirmText = "Aceptar", onConfirmClick = {loginVM.closeAlert()}) {}
        }
    }
}
