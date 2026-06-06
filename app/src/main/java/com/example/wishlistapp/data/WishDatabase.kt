package com.example.wishlistapp.data


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Wish :: class],
    version = 2,
    exportSchema = false
)


@TypeConverters(Converters::class)
abstract class WishDatabase : RoomDatabase(){
    abstract fun WishDao(): WishDao
}