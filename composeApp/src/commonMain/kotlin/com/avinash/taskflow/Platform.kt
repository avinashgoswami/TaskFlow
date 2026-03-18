package com.avinash.taskflow

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform