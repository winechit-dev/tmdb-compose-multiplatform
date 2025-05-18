package com.wcp.tmdbcmp.presentation.designsystem.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wcp.tmdbcmp.presentation.designsystem.theme.AppPreviewWrapper
import com.wcp.tmdbcmp.presentation.designsystem.theme.ThemePreviews
import com.wcp.tmdbcmp.presentation.designsystem.utils.bounceClick
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

@Composable
fun AppFilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    FilterChip(
        modifier = modifier
            .bounceClick(enabled = enabled)
            .height(31.dp),
        selected = selected,
        shape = CircleShape,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.tertiary,
            selectedLabelColor = MaterialTheme.colorScheme.onTertiary
        ),
        enabled = enabled,
        onClick = onClick,
        label = {
            Text(text = label)
        }
    )
}

@Composable
fun AppChip(
    modifier: Modifier = Modifier,
    label: String
) {

    AssistChip(
        modifier = modifier,
        shape = CircleShape,
        label = {
            Text(
                text = label,
            )
        },
        onClick = {}
    )
    /*Surface(
        modifier = modifier
            .clip(CircleShape)
            .padding(
                vertical = 8.dp,
                horizontal = 14.dp
            )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }*/

}

class ChipPreviewParameterProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(
            true,
            false
        )

}

@ThemePreviews
@Composable
private fun AppFilterChipPreview(
    @PreviewParameter(ChipPreviewParameterProvider::class) selected: Boolean
) {
    AppPreviewWrapper {
        AppFilterChip(
            selected = selected,
            label = "Popular",
            onClick = {},
            modifier = Modifier.padding(20.dp)
        )
    }
}

@ThemePreviews
@Composable
private fun AppChipPreview() {
    AppPreviewWrapper {
        AppChip(
            label = "Popular",
            modifier = Modifier.padding(20.dp)
        )
    }
}