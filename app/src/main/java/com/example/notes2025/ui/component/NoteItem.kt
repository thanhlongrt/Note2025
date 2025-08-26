package com.example.notes2025.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notes2025.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(
    title: String,
    lastEdit: String,
    contents: String,
    modifier: Modifier = Modifier,
    isSelectionEnabled: Boolean = false,
    isSelected: Boolean = false,
    onNoteClick: () -> Unit = {},
    onNoteLongClick: () -> Unit = {},
) {
    Box(
        modifier =
            modifier
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(size = 20.dp),
                    ambientColor = colorResource(R.color.color_FF6B4EFF),
                    spotColor = colorResource(R.color.color_FF6B4EFF),
                )
                .clip(RoundedCornerShape(size = 20.dp))
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                )
                .border(
                    width = if (isSelected) 0.5.dp else 0.dp,
                    color = if (isSelected) colorResource(R.color.color_FF6B4EFF)
                    else MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(size = 20.dp)
                )
                .combinedClickable(
                    onClick = {
                        onNoteClick()
                    },
                    onLongClick = {
                        onNoteLongClick()
                    },
                ),
    ) {
        AnimatedVisibility(
            modifier =
                Modifier
                    .align(Alignment.TopEnd),
            visible = isSelectionEnabled,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut(),
        ) {
            NoteCheckBox(
                modifier =
                    Modifier
                        .padding(12.dp)
                        .size(24.dp),
                checked = isSelected,
                onCheckedChange = onNoteClick,
            )
        }

        Column(
            modifier =
                modifier.padding(16.dp),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(fraction = 0.8f),
                text = title,
                fontSize = 16.sp,
                maxLines = 2,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = lastEdit,
                fontSize = 10.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = contents,
                fontSize = 14.sp,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteItemPreview() {
    val title = "This is a very very very very long title"
    val contents =
        "This is a very very very very long title This is a very very very very long title This is a very very very very long title This is a very very very very long title This is a very very very very long title"
    NoteItem(
        title = title,
        lastEdit = "Aug 19",
        contents = contents,
        isSelectionEnabled = true,
    )
}
