package com.example.nuntium.ui.profilePage

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nuntium.MainViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.example.nuntium.R
import com.example.nuntium.constants.AppThemeMode

@Composable
fun ProfileScreen(navigator: DestinationsNavigator) {
    val mainViewModel: MainViewModel = hiltViewModel()
    val appThemeMode = mainViewModel.appThemeMode.collectAsState()
    val colors = MaterialTheme.colors
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Text(
            text = "Settings", color = colors.onBackground,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(colors.surface)
                .padding(10.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Dark Mode", color = colors.onSurface)
            Switch(
                checked = appThemeMode.value is AppThemeMode.DarkMode, onCheckedChange = {
                    mainViewModel.changeAppThemeMode(it)
                }, colors = SwitchDefaults.colors(
                    checkedThumbColor = colors.primary,
                    checkedTrackColor = colors.primary,
                    uncheckedThumbColor = colors.onSurface,
                    uncheckedTrackColor = colors.onSurface
                )
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(colors.surface)
                .padding(10.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Language", color = colors.onSurface)
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = "icon",
                tint = colors.onSurface
            )
        }
    }
}