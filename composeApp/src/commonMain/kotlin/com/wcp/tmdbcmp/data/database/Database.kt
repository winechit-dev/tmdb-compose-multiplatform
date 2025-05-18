package com.wcp.tmdbcmp.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.wcp.tmdbcmp.data.database.dao.FavoriteDao
import com.wcp.tmdbcmp.data.database.entities.FavoriteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(entities = [FavoriteEntity::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

fun getRoomDatabase(builder: RoomDatabase.Builder<AppDatabase>): AppDatabase =
    builder
        // .addMigrations(MIGRATIONS)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()

internal const val DB_FILE_NAME = "tmdb-cmp.db"
