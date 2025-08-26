package com.example.notes2025.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.notes2025.R

@Composable
fun AddNotesFloatingButton(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    onClick: () -> Unit = {},
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        containerColor = colorResource(R.color.color_FF6B4EFF),
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
                    text = stringResource(R.string.button_add_new_notes),
                    modifier =
                        Modifier
                            .padding(start = 8.dp, top = 3.dp),
                    color = Color.White,
                )
            }
        }
    }
}
