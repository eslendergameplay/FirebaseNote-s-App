package com.example.jetpack12.views.notes

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter
import com.example.jetpack12.viewModels.NotesViewModel

@Composable
fun PhotoView(notesVM:NotesViewModel,idDoc:String){
    LaunchedEffect(Unit) {
        notesVM.getNoteById(idDoc)
    }

    val state = notesVM.state
    Log.d("Imagen",state.imagePath)
    Column {
        if(state.imagePath.isNotEmpty()){
            Image(painter = rememberAsyncImagePainter(state.imagePath), contentDescription = "", modifier = Modifier.fillMaxWidth().fillMaxHeight())
        }
    }
}