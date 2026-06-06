package com.example.wishlistapp

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.wishlistapp.data.WishDatabase
import com.example.wishlistapp.data.WishRepository

object Graph {
    lateinit var database: WishDatabase

    val wishRepository by lazy {
        WishRepository(wishDao = database.WishDao())
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(context,
            WishDatabase::class.java,
            "wishlist.db")
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "ALTER TABLE `wish-table` ADD COLUMN `wish-items` TEXT NOT NULL DEFAULT '[]'"
            )
        }
    }
}