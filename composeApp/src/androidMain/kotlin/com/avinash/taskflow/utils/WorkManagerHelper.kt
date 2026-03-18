package com.avinash.taskflow.utils

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.avinash.taskflow.worker.TaskWorker

actual class WorkManagerHelper(private val context: Context) {

    actual fun startWork() {
        val request = OneTimeWorkRequestBuilder<TaskWorker>().build()
        WorkManager.getInstance(context).enqueue(request)
    }
}