package com.avinash.taskflow.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avinash.taskflow.model.Task
import com.avinash.taskflow.model.TaskStats
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    stats: TaskStats,
    tasks: List<Task>,
    onCardClick: () -> Unit,
    onAddTask: (String) -> Unit,
    onToggleTask: (Int) -> Unit,
    onDeleteTask: (Int) -> Unit,
    onStartWork: () -> Unit
) {

    var showDialog by remember { mutableStateOf(false) }
    var selectedTaskId by remember { mutableStateOf<Int?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F5F9))
    ) {

        Spacer(modifier = Modifier.height(25.dp))

        // 🔷 Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Dashboard",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )

            IconButton(onClick = { onStartWork() }) {
                Icon(Icons.Default.Notifications, contentDescription = null)
            }
        }

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {

            Spacer(modifier = Modifier.height(8.dp))

            // 🔷 Stats
            StatsCard("Total Tasks", stats.total.toString(), Icons.Default.List, onCardClick)
            StatsCard("Pending", stats.pending.toString(), Icons.Default.Schedule, onCardClick)
            StatsCard("Completed", stats.completed.toString(), Icons.Default.CheckCircle, onCardClick)

            Spacer(modifier = Modifier.height(16.dp))

            // 🔥 Add Task Button
            Button(
                onClick = { showBottomSheet = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Task")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Tasks",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 🔥 Task List
            tasks.forEach { task ->

                var offsetX by remember { mutableStateOf(0f) }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp) // ✅ spacing here
                        .clip(RoundedCornerShape(12.dp))
                ) {

                    // 🔴 Background (Delete)
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(Color.Red),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }

                    // 🟡 Foreground (Swipe)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset { IntOffset(offsetX.toInt(), 0) }
                            .pointerInput(Unit) {
                                detectHorizontalDragGestures(
                                    onDragEnd = {
                                        if (offsetX < -250) {
                                            selectedTaskId = task.id
                                            showDialog = true
                                        }
                                        offsetX = 0f
                                    }
                                ) { _, dragAmount ->
                                    offsetX = (offsetX + dragAmount).coerceIn(-300f, 0f)
                                }
                            }
                    ) {
                        TaskItem(
                            task = task,
                            onToggle = { onToggleTask(task.id) },
                            onDelete = {
                                selectedTaskId = task.id
                                showDialog = true
                            }
                        )
                    }
                }
            }
        }
    }

    // 🔥 Bottom Sheet
    if (showBottomSheet) {

        var text by remember { mutableStateOf("") }

        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                Text("Add Task", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Enter task") }
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        if (text.isNotBlank()) {
                            onAddTask(text)
                            showBottomSheet = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Task")
                }
            }
        }
    }

    // 🔥 Dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        selectedTaskId?.let { onDeleteTask(it) }
                        showDialog = false
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            },
            title = { Text("Delete Task") },
            text = { Text("Are you sure you want to delete this task?") }
        )
    }
}

@Composable
fun StatsCard(
    title: String,
    value: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFFE2E8F0), shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = Color(0xFF0F172A))
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                Text(value, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(), // ✅ NO padding here
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { onToggle() }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = task.title,
                modifier = Modifier.weight(1f),
                color = if (task.isCompleted) Color.Gray else Color(0xFF0F172A)
            )

            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = null)
            }
        }
    }
}