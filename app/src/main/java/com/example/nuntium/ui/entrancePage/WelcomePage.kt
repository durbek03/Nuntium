package com.example.nuntium.ui.entrancePage

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.example.nuntium.R
import com.example.nuntium.ui.destinations.PickTopicPageDestination
import com.example.nuntium.ui.destinations.WelcomePageDestination
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import kotlin.math.abs

@OptIn(ExperimentalPagerApi::class)
@Destination
@Composable
fun WelcomePage(navigator: DestinationsNavigator) {
    val colors = MaterialTheme.colors
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val imageList =
        listOf(R.drawable.pager_image1, R.drawable.pager_image2, R.drawable.pager_image3)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colors.background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            HorizontalPager(
                state = pagerState,
                count = imageList.size,
                modifier = Modifier
                    .fillMaxHeight(0.4f)
                    .fillMaxWidth()
                    .padding(0.dp, 20.dp, 0.dp, 0.dp),
                itemSpacing = 20.dp
            ) { page ->
                Image(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(25.dp)),
                    painter = painterResource(id = imageList[page]),
                    contentDescription = "image",
                    contentScale = ContentScale.FillBounds
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .padding(0.dp, 10.dp, 0.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (i in 0..2) {
                    TabItem(
                        index = i,
                        pagerState = pagerState
                    )
                }
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 0.dp),
                text = "Nuntium App",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 0.dp),
                text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s",
                fontSize = 17.sp,
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Center
            )
            val buttonText = remember {
                mutableStateOf("Next")
            }
            buttonText.value =
                if (pagerState.currentPage == imageList.size - 1) "Get Started" else "Next"
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .padding(30.dp, 0.dp),
                onClick = {
                    coroutineScope.launch {
                        if (pagerState.currentPage != imageList.lastIndex) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            navigator.navigate(PickTopicPageDestination) {
                                this.popUpTo(WelcomePageDestination.route) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            ) {
                Text(text = buttonText.value, color = colors.onPrimary)
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabItem(modifier: Modifier = Modifier, index: Int, pagerState: PagerState) {
    val offset = pagerState.currentPageOffset
    Log.d(TAG, "TabItem: $offset")
    val animateAsDp = animateDpAsState(
        targetValue = getTabWidth(index = index, pagerState = pagerState).dp
    )
    val animateAsColor = animateColorAsState(targetValue =
        if (animateAsDp.value > 12.5.dp) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
    )

    Box(
        modifier
            .defaultMinSize(minWidth = 15.dp)
            .width(15.dp + animateAsDp.value)
            .height(15.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(animateAsColor.value)
    )
}

@OptIn(ExperimentalPagerApi::class)
fun getTabWidth(index: Int, pagerState: PagerState): Float {
    val offset = pagerState.currentPageOffset
    if (index == pagerState.currentPage + 1) {
        if (offset > 0) {
            return 25 * abs(offset)
        }
    } else if (index == pagerState.currentPage - 1) {
        if (offset < 0) {
            return 25 * abs(offset)
        }
    } else if (index == pagerState.currentPage) {
        return 25 * (1 - abs(offset))
    }
    return 0f
}