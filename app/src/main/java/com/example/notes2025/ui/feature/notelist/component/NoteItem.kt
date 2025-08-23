package com.example.notes2025.ui.feature.notelist.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(
    title: String,
    contents: String,
    isSelectable: Boolean = false,
    isSelected: Boolean = false,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    onNoteClick: () -> Unit = {},
    onNoteLongClick: () -> Unit = {},
) {
    Box(
        modifier =
            modifier
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(size = 20.dp),
                    ambientColor = Color(0x806B4EFF),
                    spotColor = Color(0x806B4EFF),
                )
                .clip(RoundedCornerShape(size = 20.dp))
                .defaultMinSize(minHeight = 120.dp)
                .background(
                    color = color,
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
        Column(
            modifier =
                modifier.padding(16.dp),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(fraction = 0.66f),
                text = title,
                fontSize = 25.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.size(size = 10.dp))
            Text(
                text = contents,
                fontSize = 20.sp,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis,
            )
        }
        if (isSelectable) {
            CustomCheckBox(
                modifier =
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .size(30.dp),
                checked = isSelected,
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
        contents = contents,
    )
}
