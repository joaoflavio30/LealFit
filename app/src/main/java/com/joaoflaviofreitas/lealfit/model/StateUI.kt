package com.joaoflaviofreitas.lealfit.model

sealed class StateUI<out T : Any> {
    data class Idle(val message: String = "") : StateUI<Nothing>()
    data class Processing(val message: String = "") : StateUI<Nothing>()
    data class Error(val message: String = "", val info: Any? = null) : StateUI<Nothing>()
    data class Processed<out T : Any>(val data: T) : StateUI<T>()

    fun idle() = this is Idle
    fun loading() = this is Processing
    fun processed() = this is Processed
    fun error() = this is Error
}