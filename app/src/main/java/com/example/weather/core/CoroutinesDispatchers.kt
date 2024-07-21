package com.example.weather.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

data class CoroutinesDispatchers(val main: CoroutineDispatcher = Main,
                                 val computation: CoroutineDispatcher = Default,
                                 val io: CoroutineDispatcher = IO)
