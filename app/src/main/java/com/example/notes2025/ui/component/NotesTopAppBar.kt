package com.example.notes2025.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NotesTopAppBar(
    modifier: Modifier = Modifier,
    startContent: @Composable () -> Unit = {},
    middleContent: @Composable () -> Unit = {},
    endContent: @Composable () -> Unit = {},
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        startContent()

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            middleContent()
        }

        endContent()
    }
}

@Preview
@Composable
fun TopAppBarPreview() {
    NotesTopAppBar()
}
