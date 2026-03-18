package com.avinash.taskflow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avinash.taskflow.model.Task
import com.avinash.taskflow.model.TaskStats
import com.avinash.taskflow.ui.approval.ApprovalScreen
import com.avinash.taskflow.ui.detail.DetailScreen
import com.avinash.taskflow.ui.dashboard.DashboardScreen
import com.avinash.taskflow.ui.map.MapScreen
import com.avinash.taskflow.viewmodel.DashboardViewModel

@Composable
fun App() {

    val viewModel = remember { DashboardViewModel() }

    val stats = viewModel.stats.value
    val tasks = viewModel.tasks.value

    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Dashboard") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                    label = { Text("Map") }
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.CheckCircle, contentDescription = null) },
                    label = { Text("Approval") }
                )
            }
        }
    ) { padding ->

        when (selectedTab) {
            0 -> DashboardScreen(
                stats = stats,
                tasks = tasks,
                onCardClick = {},
                onAddTask = { viewModel.addTask(it) },
                onToggleTask = { viewModel.toggleTask(it) },
                onDeleteTask = { viewModel.deleteTask(it) }
            )
            1 -> MapScreen()
            2 -> ApprovalScreen()
        }
    }
}