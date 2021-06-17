package org.d3if4303.hitungabsensi.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AbsensiDao {

    @Insert
    fun insert(absen: AbsensiEntity)

    @Query("SELECT * FROM absen ORDER BY id DESC")
    fun getLastAbsen(): LiveData<List<AbsensiEntity?>>
}