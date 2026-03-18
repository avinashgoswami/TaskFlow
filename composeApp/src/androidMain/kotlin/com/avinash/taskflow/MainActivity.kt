package com.avinash.taskflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.avinash.taskflow.utils.WorkManagerHelper

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val workManagerHelper = WorkManagerHelper(this)

        setContent {
            App(
                workManagerHelper = workManagerHelper
            )
        }
    }
}