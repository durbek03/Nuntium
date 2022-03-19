package com.example.nuntium.ui.homePage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nuntium.R
import com.example.nuntium.ui.homePage.states.HomePageStates
import kotlinx.coroutines.launch

@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()
    val homeViewModel: HomeViewModel = hiltViewModel()
    val focusManager = LocalFocusManager.current
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .aspectRatio(1f, true)
                .clickable {
                    if (homeViewModel.pageState.value is HomePageStates.SearchOn) {
                        focusManager.clearFocus()
                    }
                },
            painter = painterResource(
                id =
                if (homeViewModel.pageState.value is HomePageStates.CasualPage) R.drawable.ic_search else R.drawable.ic_cancel
            ),
            contentDescription = "Search",
            tint = MaterialTheme.colors.onSurface
        )
        TextField(
            value = "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .onFocusEvent {
                    if (it.isFocused) {
                        coroutineScope.launch {
                            homeViewModel.pageState.value = HomePageStates.SearchOn
                        }
                    } else {
                        coroutineScope.launch {
                            homeViewModel.pageState.value = HomePageStates.CasualPage
                        }
                    }
                },
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