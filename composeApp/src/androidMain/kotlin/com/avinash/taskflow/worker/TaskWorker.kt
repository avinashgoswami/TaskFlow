package com.avinash.taskflow.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class TaskWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.d("TaskWorker", "Background work running!")

        // 👉 You can later:
        // - Sync data
        // - Send notification
        // - Call API

        return Result.success()
    }
}