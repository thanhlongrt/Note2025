package com.example.notes2025.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.notes2025.R

@Composable
fun NoteCheckBox(
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    onCheckedChange: (() -> Unit)? = null,
) {
    Box(
        modifier =
            modifier
                .clip(RoundedCornerShape(16.dp))
                .clickable(enabled = onCheckedChange != null) {
                onCheckedChange?.invoke()
            },
    ) {
        val iconResId: Int =
            if (checked) {
                R.drawable.check_circle_fill_svgrepo_com
            } else {
                R.drawable.radio_button_unchecked_svgrepo_com
            }
        val painter = painterResource(id = iconResId)
        Icon(
            painter = painter,
            contentDescription = "checked",
            tint = colorResource(R.color.color_FF6B4EFF),
        )
    }
}
