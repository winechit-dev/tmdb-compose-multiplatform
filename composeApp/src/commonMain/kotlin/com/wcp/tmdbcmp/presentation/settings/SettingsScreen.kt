package com.wcp.tmdbcmp.presentation.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wcp.tmdbcmp.domain.model.DarkThemeConfig
import com.wcp.tmdbcmp.domain.model.SettingsDataModel
import com.wcp.tmdbcmp.domain.model.ThemeBrand
import com.wcp.tmdbcmp.presentation.designsystem.theme.AppPreviewWrapper
import com.wcp.tmdbcmp.presentation.designsystem.theme.LocalEntryPadding
import com.wcp.tmdbcmp.presentation.designsystem.theme.ThemePreviews
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
data object Settings

@Composable
fun SettingsScreen() {
    val viewModel: SettingsViewModel = koinViewModel()
    val settings by viewModel.settings.collectAsState()

    SettingsContent(
        settings = settings,
        onEvent = viewModel::onEvent
    )
}

@Composable
internal fun SettingsContent(
    settings: SettingsDataModel,
    onEvent: (SettingsEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(LocalEntryPadding.current)
            .padding(top = 20.dp),
        topBar = {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 20.dp)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {

            AnimatedVisibility(visible = settings.themeBrand == ThemeBrand.DEFAULT) {
                Column {
                    Text(
                        text = "Use Dynamic Color",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Card {
                        Column(Modifier.selectableGroup()) {
                            SettingsThemeChooserRow(
                                text = "Yes",
                                selected = settings.useDynamicColor,
                                onClick = { onEvent(SettingsEvent.ChangeDynamicColorPreference(true)) },
                            )
                            SettingsThemeChooserRow(
                                text = "No",
                                selected = !settings.useDynamicColor,
                                onClick = { onEvent(SettingsEvent.ChangeDynamicColorPreference(false)) },
                            )
                        }
                    }
                }
            }
            Column {
                Text(
                    text = "Theme preference",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Card {
                    Column(Modifier.selectableGroup()) {
                        SettingsThemeChooserRow(
                            text = "System default",
                            selected = settings.darkThemeConfig == DarkThemeConfig.FOLLOW_SYSTEM,
                            onClick = { onEvent(SettingsEvent.ChangeDarkThemeConfig(DarkThemeConfig.FOLLOW_SYSTEM)) },
                        )
                        SettingsThemeChooserRow(
                            text = "Light",
                            selected = settings.darkThemeConfig == DarkThemeConfig.LIGHT,
                            onClick = { onEvent(SettingsEvent.ChangeDarkThemeConfig(DarkThemeConfig.LIGHT)) },
                        )
                        SettingsThemeChooserRow(
                            text = "Dark",
                            selected = settings.darkThemeConfig == DarkThemeConfig.DARK,
                            onClick = { onEvent(SettingsEvent.ChangeDarkThemeConfig(DarkThemeConfig.DARK)) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsThemeChooserRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
        )
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}

@ThemePreviews
@Composable
internal fun SettingsContentPreview() {
    AppPreviewWrapper {
        SettingsContent(
            settings = SettingsDataModel(
                themeBrand = ThemeBrand.DEFAULT,
                darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                useDynamicColor = true
            ),
            onEvent = {}
        )
    }
}