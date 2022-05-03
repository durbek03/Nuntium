package com.example.nuntium.ui.profilePage

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nuntium.MainViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.example.nuntium.R

@Composable
@Destination
fun LanguagesScreen(navigator: DestinationsNavigator) {
    val colors = MaterialTheme.colors
    val languages = listOf<String>("English", "Italian", "Spanish", "French")
    val mainViewModel: MainViewModel = hiltViewModel()
    val selectedLanguage = mainViewModel.language.collectAsState()
    LaunchedEffect(key1 = true) {
        mainViewModel.canBackPress.emit(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp, 15.dp, 15.dp, 0.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Language",
                color = colors.onBackground,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_return),
                contentDescription = "icon",
                tint = colors.onSurface,
                modifier = Modifier
                    .width(23.dp)
                    .height(23.dp)
                    .align(
                        Alignment.CenterStart
                    )
                    .clickable { navigator.popBackStack() }
            )
        }
        LazyColumn(modifier = Modifier.fillMaxWidth(), state = rememberLazyListState()) {
            itemsIndexed(languages) { index, item ->
                val isSelected = item == selectedLanguage.value
                val backColor =
                    animateColorAsState(targetValue = if (isSelected) colors.primary else colors.surface)
                val textColor =
                    animateColorAsState(targetValue = if (isSelected) colors.onPrimary else colors.onSurface)
                if (index == 0) {
                    Spacer(modifier = Modifier.height(15.dp))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(color = backColor.value, shape = RoundedCornerShape(15.dp))
                        .clickable {
                            mainViewModel.changeLanguage(item)
                        }
                        .padding(15.dp, 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = item, color = textColor.value, fontWeight = FontWeight.Bold)
                    if (isSelected) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_check),
                            contentDescription = "icon",
                            tint = colors.onPrimary
                        )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}