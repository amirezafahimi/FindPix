package com.example.findpix.ui.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.findpix.R
import com.example.findpix.data.model.ImageData
import com.example.findpix.domain.entities.MappedImageData


@Preview(showBackground = true)
@Composable
fun ImageItemPreview() {
    MaterialTheme {
        Surface {
            ImageItemComposable(
                item = ImageData().mapToImageEntity(),
                modifier = Modifier.fillMaxWidth(),
                onNextScreen = {}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ImageItemComposable(
    item: MappedImageData,
    modifier: Modifier = Modifier,
    onNextScreen: (item: MappedImageData) -> Unit
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
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(item.largeImageURL)
                    .crossfade(true).build(),
                contentDescription = item.user,
                contentScale = ContentScale.Crop
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
                        style = TextStyle(color = Color.White, fontSize = 16.sp),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        item.getTags().forEach { tag ->
                            Text(
                                text = "$tag",
                                style = TextStyle(color = Color.White, fontSize = 12.sp),
                                modifier = Modifier
                                    .background(
                                        color = colorResource(id = R.color.black_50),
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
                onSearchClicked = {}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    onSearchClicked: (query: String) -> Unit
) {
    val query = remember { mutableStateOf ("") }
    val keyboardController = LocalSoftwareKeyboardController.current

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
                TextButton(
                    onClick = {
                        confirmAction()
                    }
                ) {
                    Text(text = stringResource(id = R.string.yes))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        dismissAction()
                    }
                ) {
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