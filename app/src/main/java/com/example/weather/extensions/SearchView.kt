package com.example.weather.extensions

import androidx.appcompat.widget.SearchView

fun SearchView.onQueryTextListener(onQueryTextSubmit: ((query: String) -> Unit)? = null,
                                   onQueryTextChange: ((newText: String) -> Unit)? = null) {

    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(querySubmit: String): Boolean {
            if (onQueryTextSubmit == null) return false else onQueryTextSubmit(querySubmit)
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            if (onQueryTextChange == null) return false else onQueryTextChange(newText)
            return true
        }
    })
}
