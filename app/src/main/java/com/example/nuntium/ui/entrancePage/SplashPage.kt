package com.example.nuntium.ui.entrancePage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nuntium.R
import com.example.nuntium.ui.destinations.SplashPageDestination
import com.example.nuntium.ui.destinations.WelcomePageDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Destination(start = true, route = "splash")
@Composable
fun SplashPage(navigator: DestinationsNavigator) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1C2353)),
        contentAlignment = Alignment.Center
    ) {
        val coroutineScope = rememberCoroutineScope()
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp),
                painter = painterResource(id = R.drawable.ic_nuntium),
                contentDescription = "News App"
            )
            Text(
                text = "Nuntium",
                color = Color.White,
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )

            LaunchedEffect(key1 = true) {
                coroutineScope.launch {
                    delay(1500)
                    navigator.navigate(
                        WelcomePageDestination,
                        onlyIfResumed = true, builder = {
                            this.popUpTo(SplashPageDestination.route) {
                                inclusive = true
                            }
                        })
                }
            }
        }
    }
}