package com.example.wishlistapp.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromString(value: String): List<WishItem>{
        val listType = object : TypeToken<List<WishItem>>() {}.type
        return Gson().fromJson(value,listType)
    }

    @TypeConverter
    fun fromList(list: List<WishItem>): String{
        return Gson().toJson(list)
    }

}