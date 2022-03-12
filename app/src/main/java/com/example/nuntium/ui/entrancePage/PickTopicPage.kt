package com.example.nuntium.ui.entrancePage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nuntium.constants.Constants
import com.example.nuntium.ui.appLevelComp.TopicPicker
import com.example.nuntium.ui.destinations.HomePageDestination
import com.example.nuntium.ui.destinations.PickTopicPageDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun PickTopicPage(navigator: DestinationsNavigator) {
    val colors = MaterialTheme.colors
    val topicList = Constants.TOPICS
    val pickedTopics = remember {
        mutableStateListOf<String>()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(20.dp, 0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Select your favorite topics",
                    color = colors.onBackground,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Select some of your favorite topics to let us suggest better news for you.",
                    color = colors.onSurface,
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center
                )
            }
            TopicPicker(
                modifier = Modifier
                    .fillMaxWidth(),
                topicList = topicList,
                pickedTopics = pickedTopics
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(color = colors.primary),
                onClick = {
                    navigator.navigate(HomePageDestination) {
                        this.popUpTo(PickTopicPageDestination.route) {
                            inclusive = true
                        }
                    }
                }
            ) {
                Text(text = "Next")
            }
        }
    }
}