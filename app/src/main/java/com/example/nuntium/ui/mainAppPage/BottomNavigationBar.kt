package com.example.nuntium.ui.mainAppPage

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nuntium.MainViewModel
import com.example.nuntium.R
import kotlinx.coroutines.launch

@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier, selectedPage: MainAppScreenStates) {
    val colors = MaterialTheme.colors
    val mainViewModel: MainViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_home),
            contentDescription = "Icon",
            modifier = Modifier
                .weight(1f)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() })
                {
                    coroutineScope.launch {
                        mainViewModel.mainAppScreenState.emit(MainAppScreenStates.HomePage)
                    }
                },
            tint = if (selectedPage is MainAppScreenStates.HomePage) colors.primary else colors.onBackground
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_widgets),
            contentDescription = "Icon",
            modifier = Modifier
                .weight(1f)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() })
                {
                    coroutineScope.launch {
                        mainViewModel.mainAppScreenState.emit(MainAppScreenStates.TopicPickPage)
                    }
                },
            tint = if (selectedPage is MainAppScreenStates.TopicPickPage) colors.primary else colors.onBackground
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_book),
            contentDescription = "Icon",
            modifier = Modifier
                .weight(1f)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() })
                {
                    coroutineScope.launch {
                        mainViewModel.mainAppScreenState.emit(MainAppScreenStates.SavedNewsPage)
                    }
                },
            tint = if (selectedPage is MainAppScreenStates.SavedNewsPage) colors.primary else colors.onBackground
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = "Icon",
            modifier = Modifier
                .weight(1f)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() })
                {
                    coroutineScope.launch {
                        mainViewModel.mainAppScreenState.emit(MainAppScreenStates.ProfilePage)
                    }
                },
            tint = if (selectedPage is MainAppScreenStates.ProfilePage) colors.primary else colors.onBackground
        )
    }
}