package com.groww.papertrading.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.groww.papertrading.presentation.viewmodel.PortfolioViewModel

@Composable
fun PortfolioScreen(
    userId: String,
    viewModel: PortfolioViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Portfolio") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (state.error != null) {
                Text(
                    text = "Error: ${state.error}",
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            } else if (state.portfolio != null) {
                val portfolio = state.portfolio
                
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Total Portfolio Value",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "₹${portfolio.currentValue}",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Invested", fontSize = 12.sp, color = Color.Gray)
                                Text("₹${portfolio.totalInvested}", fontWeight = FontWeight.Bold)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("Available", fontSize = 12.sp, color = Color.Gray)
                                Text("₹${portfolio.availableFunds}", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                Text(
                    text = "P&L: ₹${portfolio.totalPnl} (${portfolio.pnlPercentage}%)",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (portfolio.totalPnl >= 0) Color.Green else Color.Red
                )
            }
        }
    }
}
