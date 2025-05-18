@file:OptIn(FormatStringsInDatetimeFormats::class)

package com.wcp.tmdbcmp.data

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

val localDateTime: LocalDateTime
    get() =
        Clock.System
            .now()
            .toLocalDateTime(TimeZone.currentSystemDefault())

val LocalDateTime.millis: Long
    get() =
        toInstant(TimeZone.currentSystemDefault())
            .toEpochMilliseconds()

fun String?.changeFormat(
    originPattern: String,
    targetPattern: String,
    default: String = "-",
): String {
    if (this.isNullOrBlank()) return default
    return try {
        val localDateTime =
            LocalDateTime.parse(this, LocalDateTime.Format { byUnicodePattern(originPattern) })

        LocalDateTime
            .Format { byUnicodePattern(targetPattern) }
            .format(localDateTime)
    } catch (e: Exception) {
        default
    }
}
