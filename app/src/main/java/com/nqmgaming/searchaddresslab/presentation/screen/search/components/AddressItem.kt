package com.nqmgaming.searchaddresslab.presentation.screen.search.components

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nqmgaming.searchaddresslab.R
import com.nqmgaming.searchaddresslab.domain.model.Address
import com.nqmgaming.searchaddresslab.domain.model.Item

@Composable
fun AddressItem(
    modifier: Modifier = Modifier,
    item: Item,
    query: String
) {
    val context = LocalContext.current
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
                .background(Color.Gray.copy(alpha = 0.23f))
                .height(1.dp)

        ) {
            Spacer(modifier = Modifier.height(1.dp))
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        color = Color.Gray.copy(alpha = 0.23f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "LocationOn",
                    modifier = Modifier
                        .size(18.dp)
                        .padding(2.dp),
                    tint = Color.Black.copy(alpha = 0.8f)
                )
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // If title is same as query, then highlight the keyword
                val title = item.title
                val titleSpanStyle = if (title!!.contains(query, ignoreCase = true)) {
                    SpanStyle(
                        color = Color.Black,
                    )
                } else {
                    SpanStyle()
                }
                Text(
                    text = annotateRecursively(
                        placeHolderList = listOf(Pair(query, titleSpanStyle)),
                        originalText = title
                    ),
                    maxLines = 1,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_right_square),
                contentDescription = "Turn Right",
                tint = Color.Black.copy(alpha = 0.5f),
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                      if (item.position != null){
                          // create google map intent
                          val position = item.position

                          /*
                          * Query the position of the item
                          * daddr: destination address
                          * q: query
                          * mrt: mass rapid transit
                          * Detail: https://stackoverflow.com/questions/11419407/using-query-string-parameters-with-google-maps-api-v3-services
                           */

                          val uri = "http://maps.google.com/maps?daddr=${position.lat},${position.lng}"

                          // create intent
                          val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                          intent.setPackage("com.google.android.apps.maps")
                          context.startActivity(intent)
                      }else{
                          Toast.makeText(context, "No position found", Toast.LENGTH_SHORT).show()
                      }

                    }
                    .background(
                        shape = CircleShape,
                        color = Color.Transparent.copy(alpha = 0.1f)
                    )
            )
        }
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
            append(annotatedString)
            if (originalText.contains(item.first, ignoreCase = true)) {
                val startIndex = originalText.indexOf(item.first, ignoreCase = true)
                val endIndex = startIndex + item.first.length
                addStyle(style = item.second, start = startIndex, end = endIndex)
                addStyle(
                    style = SpanStyle(fontWeight = FontWeight.Bold),
                    start = startIndex,
                    end = endIndex
                )
            }

        }
    }
    return annotatedString
}