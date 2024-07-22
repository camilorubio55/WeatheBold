package com.example.weather

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat

inline fun <reified T> classType(): Class<T> = T::class.java

inline fun <reified T> assertThatIsInstanceOf(actual: Any?) = assertThat(
        actual, instanceOf(classType<T>())
)

fun assertThatEquals(actual: Any?, expected: Any?) = assertThat(actual, equalTo(expected))
