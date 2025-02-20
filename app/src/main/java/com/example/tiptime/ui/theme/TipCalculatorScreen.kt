package com.example.tiptime.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tiptime.R
import kotlin.math.ceil
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.platform.LocalFocusManager



@Composable
fun TipCalculatorScreen(navController: NavController, onSaveTip: (Double) -> Unit)
 {
     val focusManager = LocalFocusManager.current
    var amountInput by remember { mutableStateOf("") }
    var tipInput by remember { mutableStateOf("") }
    var peopleInput by remember { mutableStateOf("") }
    var roundUp by remember { mutableStateOf(false) }
     var showSavedMessage by remember { mutableStateOf(false) }

    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tipPercent = tipInput.toDoubleOrNull() ?: 0.0
    val numberOfPeople = peopleInput.toIntOrNull()?.takeIf { it > 0 } ?: 1

    val tip = calculateTip(amount, tipPercent, numberOfPeople, roundUp)
    val total = tip + amount
    val tipPerPerson = tip / numberOfPeople
    val totalPerPerson = total / numberOfPeople

    Column(
        modifier = Modifier
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.calculate_tip),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        EditNumberField(
            label = R.string.bill_amount,
            leadingIcon = R.drawable.money,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            value = amountInput,
            onValueChange = { amountInput = it },
            modifier = Modifier.fillMaxWidth(),


        )

        EditNumberField(
            label = R.string.how_was_the_service,
            leadingIcon = R.drawable.percent,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            value = tipInput,
            onValueChange = { tipInput = it },
            modifier = Modifier.fillMaxWidth()
        )

        EditNumberField(
            label = R.string.number_of_people,
            leadingIcon = null,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            value = peopleInput,
            onValueChange = { peopleInput = it },
            modifier = Modifier.fillMaxWidth()
        )

        RoundTheTipRow(roundUp = roundUp, onRoundUpChanged = { roundUp = it })

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.tip_amount,tip),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )


                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f), thickness = 1.dp)

                Text(
                    text = stringResource(R.string.total_amount, total),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )


                if (numberOfPeople > 1) {
                    Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f), thickness = 1.dp)

                    Text(
                        text = stringResource(R.string.tip_per_person, tipPerPerson),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )


                    Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f), thickness = 1.dp)

                    Text(
                        text = stringResource(R.string.total_per_person, totalPerPerson),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onSaveTip(tip)
                showSavedMessage = true
                focusManager.clearFocus()
               // navController.navigate("tipHistory")
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = amount > 0.0 && tipPercent > 0.0

        ) {
            Text(text = "Save Tip")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                try {
                    navController.navigate("tipHistory") {
                        launchSingleTop = true
                    }
                } catch (e: Exception) {
                    println("Navigation Error: ${e.message}")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Show History")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                amountInput = ""
                tipInput = ""
                peopleInput = ""
                roundUp = false
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Reset")
        }
        LaunchedEffect(showSavedMessage) {
            if (showSavedMessage) {
                delay(2000)
                showSavedMessage = false
            }
        }

        if (showSavedMessage) {
            Text(
                text = "Tip saved successfully!",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .wrapContentHeight(Alignment.CenterVertically)
                    .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
        }

        Text(
            text = "-Coded by Harsh 🥂-",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )


    }

}


@Composable
fun EditNumberField(
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int? = null,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        leadingIcon = leadingIcon?.let {
            { Icon(painter = painterResource(id = it), contentDescription = null) }
        },
        onValueChange = onValueChange,
        label = { Text(stringResource(label)) },
        singleLine = true,
        keyboardOptions = keyboardOptions,
        modifier = modifier.padding(bottom = 16.dp)
    )
}

@Composable
fun RoundTheTipRow(
    roundUp: Boolean,
    onRoundUpChanged: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.round_up_tip),
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = roundUp,
            onCheckedChange = onRoundUpChanged
        )
    }
}

private fun calculateTip(amount: Double, tipPercent: Double, numberOfPeople: Int, roundUp: Boolean): Double {
    var tip = (tipPercent / 100) * amount
    if (roundUp) {
        tip = ceil(tip)
    }
    return tip
}

/*@Preview(showBackground = true)
@Composable
fun TipCalculatorScreenPreview() {
    TipCalculatorScreen()
}*/
