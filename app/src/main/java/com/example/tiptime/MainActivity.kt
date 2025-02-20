package com.example.tiptime


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiptime.ui.theme.TipTimeTheme
import java.text.NumberFormat
import androidx.annotation.StringRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberBasicTooltipState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Switch
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.Divider
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.navigation.compose.*
import com.example.tiptime.ui.TipCalculatorScreen
import com.example.tiptime.ui.TipCalculatorScreen
import com.example.tiptime.ui.TipHistoryScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkTheme = isSystemInDarkTheme()
            TipTimeTheme(darkTheme = isDarkTheme) {
            val navController = rememberNavController()
            val tipHistory = remember { mutableStateListOf<Double>() }



            NavHost(navController, startDestination = "tipCalculator") {
                composable("tipCalculator") {
                    TipCalculatorScreen(navController, onSaveTip = { tip ->
                        tipHistory.add(tip)
                    })
                }
                composable("tipHistory") {
                    TipHistoryScreen(navController, tipHistory,  onDeleteTip = { tip -> tipHistory.remove(tip) }
                    )
             }
         }
      }
    }
  }
}


