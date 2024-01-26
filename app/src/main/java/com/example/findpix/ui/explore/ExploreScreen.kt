package com.example.findpix.ui.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findpix.R

class ExploreScreen {

    @Preview(showBackground = true)
    @Composable
    fun DetailsDialogPreview() {
        MaterialTheme {
            Surface {
                DetailsDialogComposable(
                    modifier = Modifier.fillMaxWidth(),
                    dismissAction = {},
                    confirmAction = {}
                )
            }
        }
    }

    @Composable
    fun DetailsDialogComposable(
        modifier: Modifier = Modifier,
        dismissAction: () -> Unit,
        confirmAction: () -> Unit
    ) {
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
                            end = 16.dp, top = 8.dp, bottom = 4.dp
                        ),
                    query = "",
                    onSearchChange = {},
                    onSearchClicked = {}
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SearchBox(
        modifier: Modifier = Modifier,
        query: String?,
        onSearchChange: (query: String) -> Unit,
        onSearchClicked: () -> Unit
    ) {
        TextField(
            modifier = modifier
                .testTag("")
                .background(MaterialTheme.colorScheme.background),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
            ), keyboardActions = KeyboardActions(onSearch = {
                onSearchClicked()
            }),
            value = query ?: "",
            onValueChange = {
                onSearchChange(it)

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
                        onSearchChange("")
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
}

