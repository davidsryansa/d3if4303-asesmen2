package org.d3if4303.hitungabsensi.data

import org.d3if4303.hitungabsensi.db.AbsensiEntity

object HitungAbsensi {
    fun hitung(data: AbsensiEntity) : HasilAbsensi{
        val absensi = 100 * (data.kehadiran.toFloat() / data.pertemuan.toFloat())
        val kategori = if (data.mkuliah) {
            when {
                absensi < 75.0 -> KategoriPresensi.TIDAKAMAN
                absensi < 90.0 -> KategoriPresensi.SANGATAMAN
                else -> KategoriPresensi.AMAN
            }
        } else {
            when {
                absensi < 70.0 -> KategoriPresensi.TIDAKAMAN
                absensi < 75.0 -> KategoriPresensi.SANGATAMAN
                else -> KategoriPresensi.AMAN
            }
        }
        return HasilAbsensi(absensi, kategori)
    }
}