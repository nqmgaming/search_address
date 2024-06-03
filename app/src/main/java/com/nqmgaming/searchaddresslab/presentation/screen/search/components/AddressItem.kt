package com.nqmgaming.searchaddresslab.presentation.screen.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import com.nqmgaming.searchaddresslab.domain.model.Address
import com.nqmgaming.searchaddresslab.domain.model.Item

@Composable
fun AddressItem(
    modifier: Modifier = Modifier,
    item: Item,
    query: String
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "LocationOn"
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            // If title is same as query, then highlight the keyword
            val title = item.title
            val titleSpanStyle = if (title!!.contains(query, ignoreCase = true)) {
                SpanStyle(color = Color.Red)
            } else {
                SpanStyle()
            }
            Text(
                text = annotateRecursively(
                    placeHolderList = listOf(Pair(query, titleSpanStyle)),
                    originalText = title
                )
            )
        }
        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "LocationOn"
        )
    }
}

@Composable
fun annotateRecursively(
    placeHolderList: List<Pair<String, SpanStyle>>,
    originalText: String
): AnnotatedString {
    var annotatedString = buildAnnotatedString { append(originalText) }
    for (item in placeHolderList) {
        annotatedString = buildAnnotatedString {
            val startIndex = annotatedString.indexOf(item.first)
            val endIndex = startIndex + item.first.length
            append(annotatedString)
            addStyle(style = item.second, start = startIndex, end = endIndex)
        }
    }
    return annotatedString
}