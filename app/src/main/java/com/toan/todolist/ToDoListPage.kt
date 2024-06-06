package com.toan.todolist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toan.todolist.database.ToDo
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoListPage (viewModel: ToDoViewModel) {
    val toDoList by viewModel.toDoList.observeAsState()
    var inputText by remember {
        mutableStateOf("")
    }

    Column (
        modifier = Modifier
            .fillMaxHeight()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            OutlinedTextField(value = inputText, onValueChange = {
                inputText = it
            },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                viewModel.addToDo(inputText)
                inputText = ""
            },
                modifier = Modifier.wrapContentWidth()
            ) {
                Text(text = "Add")
            }
        }

        toDoList?.let {
            LazyColumn(
                content = {
                    itemsIndexed(it) {index : Int, item: ToDo ->
                        ToDoItem(
                            item = item,
                            onDelete = {viewModel.deleteToDo(item.id)},
                            onUpdate = {updatedText -> viewModel.updateToDo(item.copy(title = updatedText))}
                        )
                    }

                }
            )
        }?: Text(
            text = "No items yet",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ToDoItem(item: ToDo, onDelete : () -> Unit, onUpdate : (String) -> Unit) {

    var isEditing by remember { mutableStateOf(false) }
    var updatedText by remember { mutableStateOf(item.title) }

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(20.dp)

    ) {
        Column (
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = SimpleDateFormat("HH:mm:aa, dd/MM", Locale.ENGLISH).format(item.createdAt),
                fontSize = 14.sp,
                color = Color.LightGray
            )
            if (isEditing) {
                OutlinedTextField(
                    value = updatedText,
                    onValueChange = {updatedText = it},
                    modifier = Modifier
                        .fillMaxWidth()
                        .onPreviewKeyEvent {
                            if (it.key == Key.Enter) {
                                onUpdate(updatedText)
                                isEditing = false
                                true
                            } else {
                                false
                            }
                        }
                )
            } else {
                Text(
                    text = item.title,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }

        IconButton(onClick = {
            if(isEditing) {
                onUpdate(updatedText)
            }
            isEditing = !isEditing
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_update),
                contentDescription = "Update",
                tint = Color.White
            )
        }

        IconButton(onClick = onDelete) {
            Icon(
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = "Delete",
                tint = Color.White
            )
        }
    }
}
