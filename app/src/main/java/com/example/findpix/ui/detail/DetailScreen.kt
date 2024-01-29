package com.example.findpix.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.findpix.data.model.ImageData
import com.example.findpix.domain.entities.MappedImageData


@Composable
internal fun ImageDetailScreen(imageData: MappedImageData, onBackClicked: () -> Unit) {

    DetailScreenContent(
        modifier = Modifier.fillMaxSize(),
        item = imageData,
        onBackBtnClicked = onBackClicked
    )
}

@Composable
internal fun DetailScreenContent(
    modifier: Modifier = Modifier,
    item: MappedImageData,
    onBackBtnClicked: () -> Unit
) {

    Surface(modifier = modifier) {

        Image(
            painter = rememberAsyncImagePainter(item.largeImageURL),
            contentDescription = item.user,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent, Color.Black.copy(0.8f)
                        ), startY = 500f
                    )
                )
        )

        Column(Modifier.fillMaxSize()) {

            BackButton(onBackClicked = onBackBtnClicked)

            Spacer(modifier = Modifier.weight(1f))

            DetailBottomCard(
                modifier = Modifier
                    .fillMaxWidth(),
                item = item,
            )
        }

    }
}

@Composable
fun BackButton(
    modifier: Modifier = Modifier, onBackClicked: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = { onBackClicked() }) {
        Icon(
            Icons.Filled.ArrowBack,
            contentDescription = "Favorite",
            tint = MaterialTheme.colorScheme.surfaceTint
        )

    }
}

@Composable
fun DetailBottomCard(
    modifier: Modifier = Modifier,
    item: MappedImageData
) {
    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier
            .weight(1f)
            .padding(end = 8.dp)) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "${item.user} ",
                style = TextStyle(color = Color.White, fontSize = 16.sp),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.padding(start = 8.dp)) {
                item.getTags().forEach { tag ->
                    Text(
                        text = "$tag",
                        style = TextStyle(color = Color.White, fontSize = 12.sp),
                        modifier = Modifier
                            .background(
                                color = Color.Black.copy(0.6f),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        Row {

            Spacer(modifier = Modifier.height(8.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Outlined.Favorite,
                    contentDescription = "Favorite",
                    tint = Color.White
                )
                Text(
                    text = item.likes,
                    style = TextStyle(color = Color.White, fontSize = 10.sp),
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Outlined.Comment,
                    contentDescription = "Favorite",
                    tint = Color.White
                )
                Text(
                    text = item.comments,
                    style = TextStyle(color = Color.White, fontSize = 10.sp),
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Outlined.FileDownload,
                    contentDescription = "Favorite",
                    tint = Color.White
                )
                Text(
                    text = item.downloads,
                    style = TextStyle(color = Color.White, fontSize = 10.sp),
                    fontWeight = FontWeight.Bold
                )
            }


        }

        Spacer(modifier = Modifier.width(8.dp))

    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    MaterialTheme {
        Surface {
            ImageDetailScreen(ImageData().mapToImageEntity()) {}
        }
    }
}


