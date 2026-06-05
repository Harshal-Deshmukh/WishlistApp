package com.example.wishlistapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "wish-table")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "wish-title")
    val title: String = "",
    @ColumnInfo(name = "wish-description")
    val description: String = ""
)

object DummyWish{
    val wishList = listOf(
        Wish(title = "Google Watch",
            description = "Android smarth watch"),
        Wish(title = " The Last Signal",
            description = "A stranded astronaut discovers an ancient transmission coming from deep space — but the message was sent before humans ever existed."),
        Wish(title = "Salt & Shadows",
            description = "A blind chef in a small coastal town begins to sense that one of her regular customers is not entirely human.")

    )
}