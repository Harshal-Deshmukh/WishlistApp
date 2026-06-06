package com.example.wishlistapp.data

data class WishItem(
    val id: Long = System.currentTimeMillis(),
    val title: String = "",
    val isChecked: Boolean = false
)
