package com.example.notes2025.ui.feature.notelist.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NotesTopAppBar(
    modifier: Modifier = Modifier,
    isSelectionEnabled: Boolean = false,
    allSelected: Boolean = false,
    selectedCount: Int = 0,
    onCheckedChange: (() -> Unit)? = null,
    endContent: @Composable () -> Unit = {},
) {
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
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (isSelectionEnabled) {
                CustomCheckBox(
                    modifier =
                        Modifier
                            .padding(end = 16.dp)
                            .size(30.dp),
                    checked = allSelected,
                    onCheckedChange = onCheckedChange,
                )
            }
            if (isSelectionEnabled) {
                Text(
                    text = "$selectedCount selected",
                    fontSize = 28.sp,
                )
            } else {
                Text(
                    text = "Notes",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            endContent()
        }
    }
}

@Preview
@Composable
fun TopAppBarPreviewSelectionDisabled() {
    NotesTopAppBar(
        isSelectionEnabled = false,
    )
}

@Preview
@Composable
fun TopAppBarPreviewSelectionEnabled() {
    NotesTopAppBar(
        isSelectionEnabled = true,
    )
}
