package com.example.weather.extensions

import android.view.Menu
import androidx.appcompat.widget.SearchView

fun Menu.getSearchView(searchMenuItemId: Int) = findItem(searchMenuItemId)?.actionView as? SearchView
