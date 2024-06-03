package com.nqmgaming.searchaddresslab.presentation.screen.search.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nqmgaming.searchaddresslab.domain.model.Address

@Composable
fun AddressItem(
    modifier: Modifier = Modifier,
    address: Address
) {
    Row {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "LocationOn"
        )
        Text(
            text = address.label ?: "",
            style = MaterialTheme.typography.labelMedium
        )
        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "LocationOn"
        )
    }
}