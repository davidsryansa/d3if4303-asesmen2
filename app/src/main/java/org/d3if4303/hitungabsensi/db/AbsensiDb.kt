package org.d3if4303.hitungabsensi.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AbsensiEntity::class], version = 1, exportSchema = false)
abstract class AbsensiDb : RoomDatabase() {

    abstract val dao : AbsensiDao

    companion object {

        @Volatile
        private var INSTANCE: AbsensiDb? = null

        fun getInstance(context: Context): AbsensiDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AbsensiDb::class.java,"absen"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}