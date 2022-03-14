package com.example.nuntium.ui.homePage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.nuntium.R

@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "Search",
            tint = MaterialTheme.colors.onSurface
        )
        TextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(0.7f),
            singleLine = true,
            placeholder = {
                Text(
                    text = "Search",
                    color = MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colors.onSurface,
                cursorColor = MaterialTheme.colors.onSurface,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent
            )
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_microphone),
            contentDescription = "Search",
            tint = MaterialTheme.colors.onSurface
        )
    }
}