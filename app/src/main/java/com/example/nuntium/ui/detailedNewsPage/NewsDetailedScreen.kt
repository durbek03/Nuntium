package com.example.nuntium.ui.detailedNewsPage

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.nuntium.MainViewModel
import com.example.nuntium.R
import com.example.nuntium.data.locale.News
import com.example.nuntium.ui.detailedNewsPage.DetailedNewsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Destination
@Composable
fun NewsDetailedScreen(
    news: News,
    navigator: DestinationsNavigator,
    context: Context = LocalContext.current
) {
    val viewModel: DetailedNewsViewModel = hiltViewModel()
    val colors = MaterialTheme.colors
    val savedNews = remember {
        mutableStateOf(false)
    }
    val mainViewModel: MainViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        coroutineScope.launch(Dispatchers.IO) {
            savedNews.value = viewModel.checkIfSaved(news = news)
        }
        mainViewModel.canBackPress.emit(false)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .align(Alignment.TopCenter),
            painter = rememberImagePainter(news.image),
            contentDescription = "image",
            contentScale = ContentScale.FillBounds
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .align(Alignment.TopCenter)
                .padding(15.dp, 15.dp, 15.dp, 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_return),
                            contentDescription = "rtr btn",
                            tint = Color.White,
                            modifier = Modifier
                                .width(40.dp)
                                .height(40.dp)
                                .clip(RoundedCornerShape(50.dp))
                                .background(color = colorResource(id = R.color.semiTransparent))
                                .clickable {
                                    navigator.popBackStack()
                                }
                                .padding(10.dp)
                        )
                        Icon(
                            painter = painterResource(
                                id =
                                if (savedNews.value) R.drawable.ic_save_filled else R.drawable.ic_save_unfilled
                            ),
                            contentDescription = "rtr btn",
                            tint = Color.White,
                            modifier = Modifier
                                .width(40.dp)
                                .height(40.dp)
                                .clip(RoundedCornerShape(50.dp))
                                .background(color = colorResource(id = R.color.semiTransparent))
                                .clickable {
                                    coroutineScope.launch(Dispatchers.IO) {
                                        val saved = viewModel.savingMethod(news)
                                        savedNews.value = saved
                                    }
                                }
                                .padding(10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_next),
                            contentDescription = "rtr btn",
                            tint = Color.White,
                            modifier = Modifier
                                .width(40.dp)
                                .height(40.dp)
                                .clip(RoundedCornerShape(50.dp))
                                .background(color = colorResource(id = R.color.semiTransparent))
                                .clickable {
                                    val share = Intent(Intent.ACTION_SEND)
                                    share.type = "text/plain"
                                    share.putExtra(
                                        Intent.EXTRA_TEXT,
                                        news.title + "///////" + news.content
                                    )
                                    startActivity(
                                        context,
                                        Intent.createChooser(share, "Sharing News"),
                                        null
                                    )
                                }
                                .padding(10.dp)
                        )
                    }
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = news.source,
                        color = colors.onPrimary,
                        modifier = Modifier
                            .background(
                                color = colors.primary,
                                shape = RoundedCornerShape(50.dp)
                            )
                            .padding(10.dp, 5.dp)
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = news.title,
                        color = Color.White,
                        modifier = Modifier
                            .background(
                                colorResource(id = R.color.semiTransparent),
                                shape = RoundedCornerShape(15.dp)
                            )
                            .padding(10.dp, 5.dp)
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
                .align(Alignment.BottomCenter)
                .background(colors.background)
        ) {
            Text(
                text = news.content,
                color = colors.onBackground,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(15.dp, 15.dp, 15.dp, 0.dp),
            )
        }
    }
}