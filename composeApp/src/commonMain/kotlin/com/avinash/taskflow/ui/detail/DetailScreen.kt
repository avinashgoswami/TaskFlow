package com.avinash.taskflow.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DetailScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F5F9))
            .padding(16.dp)
    ) {

        Text(
            text = "Task Details",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onBack,
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Go Back")
        }
    }
}