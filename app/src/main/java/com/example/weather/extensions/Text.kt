package com.example.weather.extensions

import android.content.Context
import androidx.annotation.StringRes
import com.example.weather.extensions.Text.StringResource
import com.example.weather.extensions.Text.StringValue

sealed class Text {
    data class StringValue(val text: String) : Text()
    class StringResource(@StringRes val resId: Int, vararg val arguments: Any) : Text() {
        override fun equals(other: Any?): Boolean {
            return when (other) {
                is StringResource -> other.resId == resId && other.arguments.contentEquals(arguments)
                else -> super.equals(other)
            }
        }

        override fun hashCode(): Int {
            return super.hashCode()
        }
    }
    companion object
}

@SuppressWarnings("SpreadOperator")
fun Text.getString(context: Context) = when (this) {
    is StringValue -> text
    is StringResource -> context.getString(resId, *arguments)
}
