package com.wcp.tmdbcmp.data

import com.wcp.tmdbcmp.data.database.DB_FILE_NAME

fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFilePath = documentDirectory() + "/$DB_FILE_NAME"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath,
    )
}
