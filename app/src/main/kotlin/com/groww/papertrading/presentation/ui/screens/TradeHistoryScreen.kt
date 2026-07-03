package com.groww.papertrading.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.groww.papertrading.presentation.viewmodel.TradeHistoryViewModel

@Composable
fun TradeHistoryScreen(
    userId: String,
    viewModel: TradeHistoryViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Trade History") },
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
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(state.filteredTrades) { trade ->
                        TradeItem(trade)
                    }
                }
            }
        }
    }
}

@Composable
fun TradeItem(trade: com.groww.papertrading.data.models.Trade) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(
                    modifier = Modifier.padding(bottom = 4.dp)
                ) {
                    Text(
                        text = trade.symbol,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = trade.type,
                        fontSize = 12.sp,
                        color = if (trade.type == "BUY") Color.Blue else Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "Qty: ${trade.quantity} @ ₹${trade.price}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "₹${trade.totalAmount}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                if (trade.pnl != 0.0) {
                    Text(
                        text = "P&L: ₹${trade.pnl}",
                        fontSize = 11.sp,
                        color = if (trade.pnl >= 0) Color.Green else Color.Red
                    )
                }
            }
        }
    }
}
