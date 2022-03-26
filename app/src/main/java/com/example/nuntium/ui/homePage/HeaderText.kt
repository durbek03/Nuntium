package com.example.nuntium.ui.homePage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeaderText() {
    val colors = MaterialTheme.colors
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(15.dp, 15.dp, 15.dp, 0.dp),
            text = "Browse",
            color = colors.onBackground,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.padding(15.dp, 0.dp),
            text = "Discover things of this world",
            color = colors.onSurface,
            fontSize = 17.sp
        )
    }
}