package com.wcp.tmdbcmp.presentation.designsystem.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.ui.tooling.preview.Preview
import tmdb_compose_multiplatform.composeapp.generated.resources.Res
import tmdb_compose_multiplatform.composeapp.generated.resources.inter_bold
import tmdb_compose_multiplatform.composeapp.generated.resources.inter_medium
import tmdb_compose_multiplatform.composeapp.generated.resources.inter_regular
import tmdb_compose_multiplatform.composeapp.generated.resources.inter_semibold
import tmdb_compose_multiplatform.composeapp.generated.resources.roboto_medium
import tmdb_compose_multiplatform.composeapp.generated.resources.roboto_regular

@Composable
fun InterFamily() =
    FontFamily(
        Font(Res.font.inter_regular, FontWeight.Normal),
        Font(Res.font.inter_medium, FontWeight.Medium),
        Font(Res.font.inter_semibold, FontWeight.SemiBold),
        Font(Res.font.inter_bold, FontWeight.Bold),
    )

@Composable
fun RobotoFamily() =
    FontFamily(
        Font(Res.font.roboto_regular, FontWeight.Normal),
        Font(Res.font.roboto_medium, FontWeight.Medium),
    )

@Composable
fun AppTypography() =
    Typography(
        displayLarge =
            TextStyle(
                fontFamily = RobotoFamily(),
                fontWeight = FontWeight.Normal,
                fontSize = 57.sp,
                lineHeight = 64.sp,
                letterSpacing = 0.5.sp,
            ),
        displayMedium =
            TextStyle(
                fontFamily = RobotoFamily(),
                fontWeight = FontWeight.Normal,
                fontSize = 45.sp,
                lineHeight = 52.sp,
                letterSpacing = 0.sp,
            ),
        displaySmall =
            TextStyle(
                fontFamily = RobotoFamily(),
                fontWeight = FontWeight.Normal,
                fontSize = 36.sp,
                lineHeight = 44.sp,
                letterSpacing = 0.sp,
            ),
        headlineLarge =
            TextStyle(
                fontFamily = RobotoFamily(),
                fontWeight = FontWeight.Normal,
                fontSize = 32.sp,
                lineHeight = 40.sp,
                letterSpacing = 0.sp,
            ),
        headlineMedium =
            TextStyle(
                fontFamily = RobotoFamily(),
                fontWeight = FontWeight.Normal,
                fontSize = 28.sp,
                lineHeight = 36.sp,
                letterSpacing = 0.sp,
            ),
        headlineSmall =
            TextStyle(
                fontFamily = RobotoFamily(),
                fontWeight = FontWeight.Normal,
                fontSize = 24.sp,
                lineHeight = 32.sp,
                letterSpacing = 0.sp,
            ),
        titleLarge =
            TextStyle(
                fontFamily = InterFamily(),
                fontWeight = FontWeight.Normal,
                fontSize = 22.sp,
                lineHeight = 28.sp,
                letterSpacing = 0.sp,
            ),
        titleMedium =
            TextStyle(
                fontFamily = InterFamily(),
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.15.sp,
            ),
        titleSmall =
            TextStyle(
                fontFamily = InterFamily(),
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp,
            ),
        labelLarge =
            TextStyle(
                fontFamily = InterFamily(),
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp,
            ),
        labelMedium =
            TextStyle(
                fontFamily = InterFamily(),
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.5.sp,
            ),
        labelSmall =
            TextStyle(
                fontFamily = InterFamily(),
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.5.sp,
            ),
        bodyLarge =
            TextStyle(
                fontFamily = InterFamily(),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
            ),
        bodyMedium =
            TextStyle(
                fontFamily = InterFamily(),
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.5.sp,
            ),
        bodySmall =
            TextStyle(
                fontFamily = InterFamily(),
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.4.sp,
            ),
    )

val Typography.defaultButton: TextStyle
    @Composable
    get() =
        TextStyle(
            fontFamily = InterFamily(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            letterSpacing = 0.sp,
        )

@Preview
@Composable
private fun TypographyPreview() {
    AppPreviewWrapper {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "displayLarge",
                style = MaterialTheme.typography.displayLarge,
            )
            Text(
                text = "displayMedium",
                style = MaterialTheme.typography.displayMedium,
            )
            Text(
                text = "displaySmall",
                style = MaterialTheme.typography.displaySmall,
            )
            Text(
                text = "headlineLarge",
                style = MaterialTheme.typography.headlineLarge,
            )
            Text(
                text = "headlineMedium",
                style = MaterialTheme.typography.headlineMedium,
            )
            Text(
                text = "headlineSmall",
                style = MaterialTheme.typography.headlineSmall,
            )

            Text(
                text = "titleLarge",
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = "titleMedium",
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = "titleSmall",
                style = MaterialTheme.typography.titleSmall,
            )

            Text(
                text = "labelLarge",
                style = MaterialTheme.typography.labelLarge,
            )
            Text(
                text = "labelMedium",
                style = MaterialTheme.typography.labelMedium,
            )
            Text(
                text = "labelSmall",
                style = MaterialTheme.typography.labelSmall,
            )
            Text(
                text = "bodyLarge",
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = "bodyMedium",
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "bodySmall",
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}
