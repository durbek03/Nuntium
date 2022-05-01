package com.example.nuntium.ui.homePage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.example.nuntium.R
import com.example.nuntium.ui.homePage.intent.HomePageIntent
import com.example.nuntium.ui.homePage.states.HomePageStates
import com.example.nuntium.ui.homePage.viewModels.HomeViewModel
import com.example.nuntium.ui.homePage.viewModels.TopicNewsViewModel
import kotlinx.coroutines.launch

@Composable
fun SearchBar(
    value: String,
    modifier: Modifier = Modifier,
    onFocusChanged: (Boolean) -> Unit,
    leftIconClicked: () -> Unit,
    onTextChange: (String) -> Unit,
    focusOnLaunch: Boolean,
    icon: Int = R.drawable.ic_search
) {
    val textFieldValue = remember {
        mutableStateOf(value)
    }
    val homeViewModel: HomeViewModel = hiltViewModel()
    val homePageState = homeViewModel.homePageState.collectAsState()
    val focusRequester = remember {
        FocusRequester
    }
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
                    leftIconClicked.invoke()
                },
            painter = painterResource(
                icon
            ),
            contentDescription = "Search",
            tint = MaterialTheme.colors.onSurface
        )
        TextField(
            value = textFieldValue.value,
            onValueChange = {
                textFieldValue.value = it
                onTextChange.invoke(it)
            },
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .onFocusEvent {
                    onFocusChanged.invoke(it.isFocused)
                }
                .focusRequester(focusRequester.Default),
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
    if (focusOnLaunch) {
        LaunchedEffect(key1 = true) {
            focusRequester.Default.requestFocus()
        }
    }
}