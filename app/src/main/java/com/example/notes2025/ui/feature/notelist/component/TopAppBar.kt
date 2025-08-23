package com.example.notes2025.ui.feature.notelist.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopAppBar(modifier: Modifier = Modifier) {
    Surface(
        modifier =
            modifier
                .fillMaxWidth()
                .height(72.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Notes",
                fontSize = 32.sp,
            )
            Row {
                AppBarButton(
                    onClick = {},
                    imageVector = Icons.Default.Search,
                )
                Spacer(
                    modifier = Modifier.width(8.dp),
                )
                AppBarButton(
                    onClick = {},
                    imageVector = Icons.Default.Info,
                )
            }
        }
    }
}

@Composable
private fun AppBarButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    imageVector: ImageVector,
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Image(
            modifier = Modifier.size(28.dp),
            imageVector = imageVector,
            contentDescription = null,
        )
    }
}

@Preview
@Composable
fun TopAppBarPreview() {
    TopAppBar()
}
