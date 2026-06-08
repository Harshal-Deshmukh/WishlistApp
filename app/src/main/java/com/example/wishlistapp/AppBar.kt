package com.example.wishlistapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material.MaterialTheme

@Composable
fun AppBarView(
    title: String,
    onBackClicked: () -> Unit = {},
    onSettingsClicked: () -> Unit = {}
) {

    val navigationIcon: (@Composable () -> Unit)? =
        if (!title.contains("WishList")) {
            {
                IconButton(onClick = { onBackClicked() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = Color.White,
                        contentDescription = null
                    )
                }
            }
        } else {
            null
        }

    TopAppBar(
        title = {
            Text(
                text = title,
                color = colorResource(id = R.color.white),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(max = 24.dp)
            )
        },
        elevation = 3.dp,
        backgroundColor = MaterialTheme.colors.primary,
        navigationIcon = navigationIcon,
        actions = {
            if (title.contains("WishList")) {
                IconButton(onClick = { onSettingsClicked() }) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        tint = Color.White,
                        contentDescription = "Settings"
                    )
                }
            }
        }
    )
}