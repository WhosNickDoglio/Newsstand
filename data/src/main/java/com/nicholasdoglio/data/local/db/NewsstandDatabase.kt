package com.nicholasdoglio.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [], exportSchema = true)
abstract class NewsstandDatabase : RoomDatabase() {
}