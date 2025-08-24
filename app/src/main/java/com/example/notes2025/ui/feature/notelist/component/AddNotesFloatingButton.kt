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
import androidx.compose.ui.unit.dp

@Composable
fun AddNotesFloatingButton(
    isSelectionEnabled: Boolean,
    isExpanded: Boolean,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = !isSelectionEnabled,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        FloatingActionButton(
            onClick = {
            },
            modifier =
                Modifier
                    .padding(
                        bottom = 56.dp,
                        end = 16.dp,
                    ).shadow(
                        elevation = 20.dp,
                        shape = RoundedCornerShape(16.dp),
                        ambientColor = Color(0xFF6B4EFF),
                        spotColor = Color(0xFF6B4EFF),
                    ),
            containerColor = Color(0xFF6B4EFF),
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
            ) {
                Image(imageVector = Icons.Default.Add, contentDescription = "Add")
                AnimatedVisibility(visible = isExpanded) {
                    Text(
                        text = "Add New Notes",
                        modifier =
                            Modifier
                                .padding(start = 8.dp, top = 3.dp),
                    )
                }
            }
        }
    }
}
