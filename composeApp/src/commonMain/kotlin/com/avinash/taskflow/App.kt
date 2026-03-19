package com.avinash.taskflow

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.avinash.taskflow.ui.approval.ApprovalScreen
import com.avinash.taskflow.ui.dashboard.DashboardScreen
import com.avinash.taskflow.ui.map.MapScreenWrapper
import com.avinash.taskflow.utils.WorkManagerHelper
import com.avinash.taskflow.viewmodel.DashboardViewModel
import kotlinx.coroutines.launch

@Composable
fun App(
    workManagerHelper: WorkManagerHelper // 🔥 injected from Android
) {

    val viewModel = remember { DashboardViewModel() }

    val stats = viewModel.stats.value
    val tasks = viewModel.tasks.value

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
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
                onAddTask = {
                    viewModel.addTask(it)

                    scope.launch {
                        snackbarHostState.showSnackbar(
                            "Task Added",
                            actionLabel = "Dismiss"
                        )
                    }
                },
                onToggleTask = {
                    viewModel.toggleTask(it)

                    scope.launch {
                        snackbarHostState.showSnackbar(
                            "Task Updated",
                            actionLabel = "Dismiss"
                        )
                    }
                },
                onDeleteTask = {
                    viewModel.deleteTask(it)

                    scope.launch {
                        snackbarHostState.showSnackbar(
                            "Task Deleted",
                            actionLabel = "Dismiss"
                        )
                    }
                },

                // 🔥 WorkManager trigger
                onStartWork = {
                    workManagerHelper.startWork()
                }
            )

            1 -> MapScreenWrapper()

            2 -> ApprovalScreen()
        }
    }
}