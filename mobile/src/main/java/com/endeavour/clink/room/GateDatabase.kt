package com.endeavour.clink.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.endeavour.clink.model.Gate
import com.endeavour.clink.room.RoomContract.Companion.DATABASE_GATE

@Database(
        entities = [Gate::class],
        version = 1,
        exportSchema = false
)
abstract class GateDatabase : RoomDatabase() {

    abstract fun gatesDao(): GateDao

    companion object {

        @Volatile
        private var INSTANCE: GateDatabase? = null

        fun getInstance(context: Context): GateDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE
                            ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        GateDatabase::class.java, DATABASE_GATE)
                        .build()
    }
}