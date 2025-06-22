package com.aubynsamuel.progsy.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aubynsamuel.progsy.ui.components.FocusSetup
import com.aubynsamuel.progsy.ui.viewmodel.FocusSessionViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FocusScreen(
    viewModel: FocusSessionViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🎯 Lock-In Mode") }
            )
        }
    ) { paddingValues ->
        if (uiState.isSessionActive) {
            ActiveFocusSession(
                uiState = uiState,
                onPause = viewModel::pauseSession,
                onResume = viewModel::resumeSession,
                onStop = viewModel::stopSession,
                modifier = Modifier.padding(paddingValues)
            )
        } else {
            FocusSetup(
                onStartSession = viewModel::startFocusSession,
                modifier = Modifier.padding(paddingValues)
            )
        }

        if (uiState.showCompletionDialog) {
            FocusCompletionDialog(
                onDismiss = viewModel::dismissCompletionDialog,
                onSave = viewModel::saveFocusRating
            )
        }
    }
}

