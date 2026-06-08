package com.example.wishlistapp.ui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.sp
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wishlistapp.AppBarView
import com.example.wishlistapp.Screen
import com.example.wishlistapp.data.Wish
import com.example.wishlistapp.WishViewModel
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import com.example.wishlistapp.data.WishItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeView(
    navController: NavController,
    viewModel: WishViewModel
) {
    val context = LocalContext.current
    val primaryColor = MaterialTheme.colors.primary

    Scaffold(
        topBar = {
            AppBarView(
                title = "WishList",
                onSettingsClicked = {
                    navController.navigate(Screen.SettingsScreen.route)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all = 20.dp),
                contentColor = Color.White,
                containerColor = primaryColor,
                onClick = {
                    navController.navigate(Screen.AddScreen.route + "/0L")
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(primaryColor.copy(alpha = 0.1f))
                .padding(it)
        ) {
            val wishlist = viewModel.getAllWishes.collectAsState(initial = listOf())

            if (wishlist.value.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = primaryColor.copy(alpha = 1.0f),
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No Wishes Yet!",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor.copy(alpha = 1.0f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Tap + to add your first wish",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    items(wishlist.value) { wish ->
                        val dismissState = rememberDismissState(
                            confirmStateChange = {
                                if (it == DismissValue.DismissedToEnd ||
                                    it == DismissValue.DismissedToStart
                                ) {
                                    viewModel.deleteWish(wish)
                                }
                                true
                            }
                        )
                        SwipeToDismiss(
                            state = dismissState,
                            background = {
                                val color by animateColorAsState(
                                    if (dismissState.dismissDirection == DismissDirection.EndToStart)
                                        //primaryColor
                                        Color(0xFFE53935)
                                    else Color.Transparent,
                                    label = ""
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color)
                                        .padding(horizontal = 20.dp),
                                    contentAlignment = Alignment.CenterEnd
                                ) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Delete",
                                        tint = Color.White
                                    )
                                }
                            },
                            directions = setOf(DismissDirection.EndToStart),
                            dismissThresholds = { FractionalThreshold(0.25f) },
                            dismissContent = {
                                WishItem(
                                    wish = wish,
                                    primaryColor = primaryColor,
                                    onWishItemChecked = { item, isChecked ->
                                        val updatedItems = Gson().fromJson<List<WishItem>>(
                                            wish.items,
                                            object : TypeToken<List<WishItem>>() {}.type
                                        ).map {
                                            if (it.id == item.id) it.copy(isChecked = isChecked)
                                            else it
                                        }
                                        viewModel.updateWish(
                                            wish.copy(items = Gson().toJson(updatedItems))
                                        )
                                    }
                                ) {
                                    navController.navigate(Screen.AddScreen.route + "/${wish.id}")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WishItem(
    wish: Wish,
    primaryColor: Color,
    onWishItemChecked: (WishItem, Boolean) -> Unit,
    onClick: () -> Unit
) {
    val items: List<WishItem> = remember(wish.items) {
        try {
            Gson().fromJson(
                wish.items,
                object : TypeToken<List<WishItem>>() {}.type
            ) ?: listOf()
        } catch (e: Exception) {
            listOf()
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { onClick() },
        elevation = 6.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(color = primaryColor, shape = CircleShape)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = wish.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF212121)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = wish.description,
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }
            }

            if (items.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = primaryColor.copy(alpha = 0.4f))
                Spacer(modifier = Modifier.height(8.dp))

                items.forEach { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = item.isChecked,
                            onCheckedChange = { onWishItemChecked(item, it) },
                            colors = CheckboxDefaults.colors(checkedColor = Color(0xFF4CAF50))
                        )
                        Text(
                            text = item.title,
                            fontSize = 13.sp,
                            color = if (item.isChecked) Color.Gray else Color.Black,
                            style = TextStyle(
                                textDecoration = if (item.isChecked)
                                    TextDecoration.LineThrough
                                else TextDecoration.None
                            )
                        )
                    }
                }
            }
        }
    }
}