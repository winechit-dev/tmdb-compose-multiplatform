package com.wcp.tmdbcmp.data.datastore.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.okio.OkioSerializer
import com.wcp.tmdbcmp.data.datastore.model.DarkThemeConfigResponse
import com.wcp.tmdbcmp.data.datastore.model.SettingsDataResponse
import com.wcp.tmdbcmp.data.datastore.model.ThemeBrandResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource
import okio.use

internal object SettingDataSerializer : OkioSerializer<SettingsDataResponse> {
    override val defaultValue: SettingsDataResponse
        get() =
            SettingsDataResponse(
                themeBrand = ThemeBrandResponse.DEFAULT,
                darkThemeConfig = DarkThemeConfigResponse.FOLLOW_SYSTEM,
                useDynamicColor = false,
            )

    override suspend fun readFrom(source: BufferedSource): SettingsDataResponse =
        try {
            Json.decodeFromString(SettingsDataResponse.serializer(), source.readUtf8())
        } catch (serialization: SerializationException) {
            throw CorruptionException("Unable to read Settings", serialization)
        }

    override suspend fun writeTo(
        t: SettingsDataResponse,
        sink: BufferedSink,
    ) {
        withContext(Dispatchers.IO) {
            sink.use {
                it.writeUtf8(
                    Json.encodeToString(SettingsDataResponse.serializer(), t),
                )
            }
        }
    }
}
