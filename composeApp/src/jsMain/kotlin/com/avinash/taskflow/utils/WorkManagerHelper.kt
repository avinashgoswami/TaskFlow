package com.avinash.taskflow.utils

actual class WorkManagerHelper {

    actual fun startWork() {
        println("WorkManager not supported on JS")
    }
}