package com.wcp.tmdbcmp.data.mapper

import com.wcp.tmdbcmp.data.datastore.model.DarkThemeConfigResponse
import com.wcp.tmdbcmp.data.datastore.model.SettingsDataResponse
import com.wcp.tmdbcmp.data.datastore.model.ThemeBrandResponse
import com.wcp.tmdbcmp.domain.model.DarkThemeConfig
import com.wcp.tmdbcmp.domain.model.SettingsDataModel
import com.wcp.tmdbcmp.domain.model.ThemeBrand

fun SettingsDataResponse.toSettingsDataModel(): SettingsDataModel =
    SettingsDataModel(
        themeBrand =
            when (themeBrand) {
                ThemeBrandResponse.DEFAULT -> ThemeBrand.DEFAULT
                ThemeBrandResponse.ANDROID -> ThemeBrand.ANDROID
            },
        darkThemeConfig =
            when (darkThemeConfig) {
                DarkThemeConfigResponse.FOLLOW_SYSTEM -> DarkThemeConfig.FOLLOW_SYSTEM
                DarkThemeConfigResponse.LIGHT -> DarkThemeConfig.LIGHT
                DarkThemeConfigResponse.DARK -> DarkThemeConfig.DARK
            },
        useDynamicColor = useDynamicColor,
    )
