package com.example.weather.extensions

open class Event<out T>(private val content: T) {

    var consumed = false
        private set

    fun consume(): T? =
            if (!consumed) {
                consumed = true
                content
            } else null

    fun peek(): T = content

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Event<*>

        if (content != other.content) return false
        if (consumed != other.consumed) return false

        return true
    }

    override fun hashCode(): Int {
        var result = content?.hashCode() ?: 0
        result = 31 * result + consumed.hashCode()
        return result
    }
}
