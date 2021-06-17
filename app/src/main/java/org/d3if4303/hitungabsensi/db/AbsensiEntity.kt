package org.d3if4303.hitungabsensi.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "absen")
class AbsensiEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var tanggal: Long = System.currentTimeMillis(),
    var kehadiran: Float,
    var pertemuan: Float,
    var mkuliah: Boolean
)