package com.example.wishlistapp

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wishlistapp.data.Wish
import com.example.wishlistapp.data.WishItem
import com.example.wishlistapp.data.WishRepository
import com.example.wishlistapp.data.ThemeDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WishViewModel(
    application: Application,
) : AndroidViewModel(application) {
    private val wishRepository: WishRepository = Graph.wishRepository

    var wishTitleState by mutableStateOf("")
    var wishDescriptionState by mutableStateOf("")
    var wishItemsState by mutableStateOf(listOf<WishItem>())

    // Theme
    private val themeDataStore = ThemeDataStore(application)

    val selectedColor = themeDataStore.selectedColor
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "Pink"
        )

    fun saveColor(color: String) {
        viewModelScope.launch {
            themeDataStore.saveColor(color)
        }
    }

    fun onWishTitleChanged(newString: String) {
        wishTitleState = newString
    }

    fun onWishDescriptionChanged(newString: String) {
        wishDescriptionState = newString
    }

    lateinit var getAllWishes: Flow<List<Wish>>

    init {
        viewModelScope.launch {
            getAllWishes = wishRepository.getWishes()
        }
    }

    fun addWish(wish: Wish) {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.addWish(wish = wish)
        }
    }

    fun getAWishById(id: Long): Flow<Wish> {
        return wishRepository.getAWishById(id)
    }

    fun updateWish(wish: Wish) {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.updateAWish(wish = wish)
        }
    }

    fun deleteWish(wish: Wish) {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.deleteAWish(wish = wish)
        }
    }

    fun onWishItemAdded(item: WishItem) {
        wishItemsState = wishItemsState + item
    }

    fun onWishItemChecked(item: WishItem, isChecked: Boolean) {
        wishItemsState = wishItemsState.map {
            if (it.id == item.id) it.copy(isChecked = isChecked)
            else it
        }
    }

    fun onWishItemDeleted(item: WishItem) {
        wishItemsState = wishItemsState - item
    }
}