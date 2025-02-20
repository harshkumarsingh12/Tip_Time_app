package com.example.tiptime.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tiptime.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.snapshots.SnapshotStateList

@Composable
fun TipHistoryScreen(navController: NavController, tipHistory: List<Double>, onDeleteTip: (Double) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.tip_history),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (tipHistory.isEmpty()) {
            Text(
                text = stringResource(R.string.no_tip_history),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            Box(modifier = Modifier.weight(1f)) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(tipHistory) { tip ->
                        TipHistoryItem(tip = tip, onDelete = onDeleteTip)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.back_to_calculator))
        }
    }
}



@Composable
fun TipHistoryItem(tip: Double, onDelete: (Double) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.saved_tip, String.format("%.2f", tip)),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { onDelete(tip) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Tip"
                )
            }
        }
    }
}