package com.aubynsamuel.progsy.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aubynsamuel.progsy.ui.components.EveningReflection
import com.aubynsamuel.progsy.ui.components.MorningCheckIn
import com.aubynsamuel.progsy.ui.viewmodel.DailyCheckInViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyCheckInScreen(
    viewModel: DailyCheckInViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Morning", "Evening")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daily Check-In") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TabRow(selectedTabIndex = tabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index }
                    )
                }
            }

            when (tabIndex) {
                0 -> MorningCheckIn(
                    checkIn = uiState.currentCheckIn,
                    onSubmit = viewModel::updateMorningCheckIn
                )

                1 -> EveningReflection(
                    checkIn = uiState.currentCheckIn,
                    onSubmit = viewModel::updateEveningReflection
                )
            }
        }
    }
}