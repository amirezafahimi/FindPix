package com.example.findpix.ui.explore

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.example.findpix.R
import com.example.findpix.data.model.ImageResponse
import com.example.findpix.domain.entity.ImageItem
import com.example.findpix.util.NetworkUtils


@Composable
fun ExploreScreen(
    viewModel: ExploreViewModel = hiltViewModel(),
    onNextScreen: (item: ImageItem) -> Unit
) {
    val isInitialized = rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    LaunchedEffect(isInitialized) {
        if (!isInitialized.value) {
            if (NetworkUtils.isOnline(context = context)) {
                viewModel.initOnlineMode()
            } else {
                viewModel.initOfflineMode()
            }
            isInitialized.value = true
        }
    }
    ExploreScreenContent(
        modifier = Modifier.fillMaxSize(),
        fetchData = viewModel::searchImage,
        uiState = viewModel.uiState.collectAsState().value,
        lastQueryState = viewModel.lastQueryState.collectAsState().value,
        onNextScreen = onNextScreen
    )
}

@Composable
fun LoadingComposable() {
    Column {
        val configuration = LocalConfiguration.current

        val columnsCount = when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> 2
            else -> 1
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(columnsCount),
            modifier = Modifier.padding(10.dp)
        ) {
            items(20) {
                LoadingShimmer()
            }
        }
    }
}

@Composable
internal fun ExploreScreenContent(
    modifier: Modifier = Modifier,
    uiState: SearchResultState,
    lastQueryState: String,
    fetchData: (query: String) -> Unit,
    onNextScreen: (item: ImageItem) -> Unit
) {

    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = modifier) {

            SearchBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp, top = 16.dp, bottom = 4.dp
                    ),
                onSearchClicked = { query ->
                    fetchData(query)
                },
                lastQueryState = lastQueryState
            )

            Spacer(modifier = Modifier.height(4.dp))

            if (uiState.isLoading) {
                LoadingComposable()
            } else {
                val configuration = LocalConfiguration.current

                val columnsCount = when (configuration.orientation) {
                    Configuration.ORIENTATION_LANDSCAPE -> 2
                    else -> 1
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(columnsCount),
                    modifier = Modifier.padding(10.dp)
                ) {
                    items(uiState.success.size) {
                        ImageItemComposable(
                            modifier = Modifier.fillMaxWidth(),
                            item = uiState.success[it],
                            onNextScreen = onNextScreen
                        )
                    }
                }
            }

            uiState.error?.let { error ->
                Toast.makeText(LocalContext.current, error, Toast.LENGTH_LONG).show()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DetailsDialogPreview() {
    MaterialTheme {
        Surface {
            DetailsDialogComposable(
                modifier = Modifier.fillMaxWidth(),
                showDialog = false,
                dismissAction = {},
                confirmAction = {}
            )
        }
    }
}

@Composable
fun DetailsDialogComposable(
    modifier: Modifier = Modifier,
    showDialog: Boolean,
    dismissAction: () -> Unit,
    confirmAction: () -> Unit
) {
    if (showDialog)
        AlertDialog(
            modifier = modifier.testTag(""),
            onDismissRequest = { dismissAction() },
            confirmButton = {
                TextButton(onClick = { confirmAction() }) {
                    Text(text = stringResource(id = R.string.yes))
                }
            },
            dismissButton = {
                TextButton(onClick = { dismissAction() }) {
                    Text(text = stringResource(id = R.string.no))
                }
            },
            text = {
                Text(
                    text = stringResource(id = R.string.ask_more_details)
                )
            }
        )
}

@Preview(showBackground = true)
@Composable
fun ImageItemPreview() {
    MaterialTheme {
        Surface {
            ImageItemComposable(
                item = ImageResponse().mapToImageEntity(),
                modifier = Modifier.fillMaxWidth(),
                onNextScreen = {}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ImageItemComposable(
    item: ImageItem,
    modifier: Modifier = Modifier,
    onNextScreen: (item: ImageItem) -> Unit
) {
    val showDialog = remember { mutableStateOf(false) }

    Card(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 4.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = {
            showDialog.value = true
        }) {
        if (showDialog.value) {
            DetailsDialogComposable(
                showDialog = showDialog.value,
                dismissAction = { showDialog.value = false }) {
                showDialog.value = false
                onNextScreen(item)
            }
        }
        Box(modifier = Modifier.height(200.dp)) {
            val imageLoader = ImageLoader.Builder(LocalContext.current)
                .respectCacheHeaders(false).build()
            Image(
                painter = rememberAsyncImagePainter(
                    item.largeImageURL,
                    imageLoader = imageLoader
                ),
                contentDescription = item.user,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent, Color.Black
                            ), startY = 350f
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Column {
                    Text(
                        text = "${item.user} ",
                        style = TextStyle(color = Color.White, fontSize = 14.sp),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        item.getTags().forEach { tag ->
                            Text(
                                text = tag,
                                style = TextStyle(color = Color.White, fontSize = 12.sp),
                                modifier = Modifier
                                    .background(
                                        color = Color.Black.copy(0.5f),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBoxPreview() {
    MaterialTheme {
        Surface {
            SearchBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp, top = 16.dp, bottom = 4.dp
                    ),
                onSearchClicked = {},
                lastQueryState = ""
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    onSearchClicked: (query: String) -> Unit,
    lastQueryState: String
) {
    val query = rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(lastQueryState) {
        query.value = lastQueryState
    }

    TextField(
        modifier = modifier
            .testTag("")
            .background(MaterialTheme.colorScheme.background),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ), keyboardActions = KeyboardActions(onSearch = {
            onSearchClicked(query.value)
            keyboardController?.hide()
        }),
        value = query.value,
        onValueChange = {
            query.value = it
        },
        shape = CircleShape,
        placeholder = { Text("Search Image") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search, contentDescription = null
            )
        },
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable(
                    onClickLabel = stringResource(id = R.string.cd_clear_search)
                ) {
                    query.value = ""
                },
                imageVector = Icons.Default.Cancel, contentDescription = null
            )
        }, singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun LoadingShimmer() {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    ShimmerGridItem(brush = brush)
}

@Composable
fun ShimmerGridItem(brush: Brush) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            Spacer(
                modifier = Modifier
                    .height(200.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth(fraction = 1f)
                    .background(brush)
            )
        }
    }
}
