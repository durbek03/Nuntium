package com.example.nuntium.ui.selectTopicPage

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nuntium.constants.Constants
import com.example.nuntium.ui.appLevelComp.TopicPicker
import com.example.nuntium.ui.homePage.viewModels.RecommendedNewsViewModel
import kotlinx.coroutines.launch

@Composable
fun PickTopicScreen() {
    val coroutineScope = rememberCoroutineScope()
    val viewModel: RecommendedNewsViewModel = hiltViewModel()
    val colors = MaterialTheme.colors
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.padding(15.dp, 15.dp, 15.dp, 0.dp),
                text = "Categories",
                color = colors.onBackground,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.padding(15.dp, 0.dp),
                text = "Thousands of articles in each category",
                color = colors.onSurface,
                fontSize = 17.sp
            )
        }
        TopicPicker(
            modifier = Modifier.fillMaxWidth(),
            topicList = Constants.TOPICS,
            pickedTopics = viewModel.pickedTopics
        ) {
            coroutineScope.launch {
                viewModel.query.emit(it)
            }
        }
    }
}