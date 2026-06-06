package com.example.wishlistapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wishlistapp.data.Wish
import com.example.wishlistapp.data.WishItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(
    id: Long,
    viewModel: WishViewModel,
    navController: NavController
) {
    val snackMessage = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()


    if (id != 0L) {
        val wish = viewModel.getAWishById(id)
            .collectAsState(initial = Wish(0L, "", ""))
        viewModel.wishTitleState = wish.value.title
        viewModel.wishDescriptionState = wish.value.description
        if (viewModel.wishItemsState.isEmpty()) {
            viewModel.wishItemsState =
                Gson().fromJson(
                    wish.value.items,
                    object : TypeToken<List<WishItem>>() {}.type
                )
        }
    } else {
        viewModel.wishTitleState = ""
        viewModel.wishDescriptionState = ""
        viewModel.wishItemsState = listOf()
    }


    Scaffold(
        topBar = {
            AppBarView(
                title = if (id != 0L)
                    stringResource(id = R.string.update_wish)
                else
                    stringResource(id = R.string.add_wish)
            ) { navController.navigateUp() }
        },
        scaffoldState = scaffoldState,
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            WishTextField(
                label = "Title",
                value = viewModel.wishTitleState,
                onValueChanged = { viewModel.onWishTitleChanged(it) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            WishTextField(
                label = "Description",
                value = viewModel.wishDescriptionState,
                onValueChanged = { viewModel.onWishDescriptionChanged(it) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            //  Checklist Section
            Divider(color = Color(0xFFE91E63))
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Checklist",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE91E63)
                ),
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Items List
            viewModel.wishItemsState.forEach { item ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = item.isChecked,
                        onCheckedChange = {
                            viewModel.onWishItemChecked(item, it)
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color(0xFFE91E63)
                        )
                    )
                    Text(
                        text = item.title,
                        modifier = Modifier.weight(1f),
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = if (item.isChecked) Color.Gray else Color.Black,
                            textDecoration = if (item.isChecked)
                                TextDecoration.LineThrough
                            else TextDecoration.None
                        )
                    )
                    IconButton(onClick = { viewModel.onWishItemDeleted(item) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Item",
                            tint = Color(0xFFE91E63)
                        )
                    }
                }
            }

            // Add Item Row
            val newItemText = remember { mutableStateOf("") }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    WishTextField(
                        label = "Add Item",
                        value = newItemText.value,
                        onValueChanged = { newItemText.value = it }
                    )
                }
                IconButton(
                    onClick = {
                        if (newItemText.value.isNotEmpty()) {
                            viewModel.onWishItemAdded(
                                WishItem(title = newItemText.value)
                            )
                            newItemText.value = ""
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Item",
                        tint = Color(0xFFE91E63)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))


            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFE91E63),
                    contentColor = Color.White
                ),
                onClick = {
                    if (viewModel.wishTitleState.isNotEmpty()) {
                        if (id != 0L) {
                            // Update
                            viewModel.updateWish(
                                Wish(
                                    id = id,
                                    title = viewModel.wishTitleState.trim(),
                                    description = viewModel.wishDescriptionState.trim(),
                                    items = Gson().toJson(viewModel.wishItemsState)
                                )
                            )
                        } else {
                            // Add
                            viewModel.addWish(
                                Wish(
                                    title = viewModel.wishTitleState.trim(),
                                    description = viewModel.wishDescriptionState.trim(),
                                    items = Gson().toJson(viewModel.wishItemsState)
                                )
                            )
                            snackMessage.value = "Wish has been created"
                        }
                    } else {
                        snackMessage.value = "Enter fields to create a wish"
                    }

                    scope.launch {
                        navController.navigateUp()
                    }
                }
            ) {
                Text(
                    text = if (id != 0L)
                        stringResource(id = R.string.update_wish)
                    else
                        stringResource(id = R.string.add_wish),
                    style = TextStyle(fontSize = 18.sp)
                )
            }
        }
    }
}


@Composable
fun WishTextField(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = label, color = Color.Black) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.black),
            unfocusedBorderColor = colorResource(id = R.color.black),
            cursorColor = colorResource(id = R.color.black),
            focusedLabelColor = colorResource(id = R.color.black),
            unfocusedLabelColor = colorResource(id = R.color.black),
        )
    )
}