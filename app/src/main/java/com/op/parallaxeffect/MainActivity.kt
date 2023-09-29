package com.op.parallaxeffect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.op.parallaxeffect.ui.theme.ParrallaxEffectTheme
import kotlin.math.absoluteValue

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ParrallaxEffectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    val pagerState = rememberPagerState()
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        HorizontalPager(
                            pageCount = 3,
                            state = pagerState,
                            pageSize = PageSize.Fixed(300.dp),
                            pageSpacing = 10.dp,
                            contentPadding = PaddingValues(
                                horizontal = 30.dp,
                                vertical = 10.dp
                            )
                        ) { page ->
                            when (page) {
                                0 -> {
                                    PageView(
                                        url = "https://fastly.picsum.photos/id/13/2500/1667.jpg?hmac=SoX9UoHhN8HyklRA4A3vcCWJMVtiBXUg0W4ljWTor7s",
                                        pagerState,
                                        page
                                    )
                                }

                                1 -> {
                                    PageView(
                                        url = "https://fastly.picsum.photos/id/49/1280/792.jpg?hmac=NnUJy0O9-pXHLmY2loqVs2pJmgw9xzuixgYOk4ALCXU",
                                        pagerState,
                                        page
                                    )
                                }

                                else -> {
                                    PageView(
                                        url = "https://fastly.picsum.photos/id/29/4000/2670.jpg?hmac=rCbRAl24FzrSzwlR5tL-Aqzyu5tX_PA95VJtnUXegGU",
                                        pagerState,
                                        page
                                    )
                                }
                            }

                        }

                        HorizontalPagerIndicator(
                            pageCount = 3,
                            pagerState = pagerState
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PageView(url: String, pagerState: PagerState, page: Int) {
    Card(
        modifier = Modifier
            .size(width = 300.dp, height = 200.dp)
    ) {
        AsyncImage(
            model = url,
            contentDescription = "",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .wrapContentWidth(unbounded = true)
                .graphicsLayer {
                    val pageOffset =
                        (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                    translationX = (300 / 1.5f) * pageOffset
                }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HorizontalPagerIndicator(
    pageCount: Int,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    indicatorColor: Color = Color.Black,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .wrapContentSize()
            .height(15.dp)
    ) {

        repeat(pageCount) { page ->
            val (color, width) =
                if (pagerState.currentPage == page || pagerState.targetPage == page) {
                    val pageOffset =
                        ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                    val offsetPercentage = 1f - pageOffset.coerceIn(0f, 1f)

                    val width = (8f * 2) + (pageCount * offsetPercentage)
                    indicatorColor.copy(
                        alpha = offsetPercentage
                    ) to width
                } else {
                    indicatorColor.copy(alpha = 0.1f) to 8f
                }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(color)
                    .width(width.dp)
                    .height(8.dp)
            )
        }
    }
}
