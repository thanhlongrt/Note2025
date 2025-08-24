package com.example.notes2025.ui.feature.notelist.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp

@Composable
fun AddNotesFloatingButton(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    onClick: () -> Unit = {},
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        containerColor = Color(0xFF6B4EFF),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            Image(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                colorFilter = ColorFilter.tint(Color.White),
            )
            AnimatedVisibility(visible = isExpanded) {
                Text(
                    text = "Add New Notes",
                    modifier =
                        Modifier
                            .padding(start = 8.dp, top = 3.dp),
                    color = Color.White,
                )
            }
        }
    }
}
