package com.example.jetpack12.views.notes

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.jetpack12.R
import com.example.jetpack12.viewModels.NotesViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteView(navC:NavController,notesVM:NotesViewModel,idDoc:String){

    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(context,context.packageName + ".provider",file)
    var image by remember { mutableStateOf<Uri>(Uri.EMPTY) }
    val imageDefault = R.drawable.photo
    val permissionCheckResult = ContextCompat.checkSelfPermission(context,android.Manifest.permission.CAMERA)
    val cameraLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) {
        image = uri
    }
    val permissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
        if(it !=null){
            cameraLauncher.launch(uri)
        }else{
            Toast.makeText(context,"Permiso Denegado", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        notesVM.getNoteById(idDoc)
    }
    val state = notesVM.state
    Scaffold (topBar = { TopAppBar(title = { Text(text = "Editar Nota.")}, navigationIcon = { IconButton(
        onClick = {navC.popBackStack()}) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
    }}, actions = { IconButton(onClick = { notesVM.updateNote(idDoc,image) {
        navC.popBackStack()
    } }) {
        Icon(imageVector = Icons.Default.Edit, contentDescription = "")
    }
        IconButton(onClick = {notesVM.deleteNote(idDoc,state.imagePath){navC.popBackStack()} }) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "")
        }
    })}){pad->
        Column (modifier = Modifier
            .padding(pad)
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
            OutlinedTextField(value = state.title, onValueChange = {notesVM.onValueChange(it,"title")}, label = { Text(
                text = "Titulo :")},modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp))
            OutlinedTextField(value = state.note, onValueChange = {notesVM.onValueChange(it,"note")}, label = { Text(
                text = "Nota :"
            )},modifier = Modifier.fillMaxWidth().height(400.dp).padding(start = 20.dp,end= 20.dp,bottom = 20.dp))
            Image(modifier = Modifier
                .clickable {
                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        cameraLauncher.launch(uri)
                    } else {
                        permissionLauncher.launch(android.Manifest.permission.CAMERA)
                    }
                }
                .padding(16.dp, 8.dp), painter = rememberAsyncImagePainter(if(image.path?.isNotEmpty() == true)image else if (state.imagePath.isNotEmpty())state.imagePath else imageDefault), contentDescription = "")

        }
    }
}

