package org.d3if4303.hitungabsensi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.d3if4303.hitungabsensi.data.HasilAbsensi
import org.d3if4303.hitungabsensi.data.KategoriPresensi

class PerhitunganViewModel: ViewModel() {

    //Hasil Absensi bisa null jika pengguna belum menghitung Absensi
    private val hasilAbsensi = MutableLiveData<HasilAbsensi?>()

    fun hitungAbsensi(kehadiran: String, pertemuan: String, mkuliah: Boolean){
        val absensi = 100 * (kehadiran.toFloat() / pertemuan.toFloat())
        val kategori = if (mkuliah) {
            when {
                absensi < 75.0 -> KategoriPresensi.TIDAKAMAN
                absensi >= 90.0 -> KategoriPresensi.SANGATAMAN
                else -> KategoriPresensi.AMAN
            }
        } else {
            when {
                absensi < 70.0 -> KategoriPresensi.TIDAKAMAN
                absensi >= 75.0 -> KategoriPresensi.SANGATAMAN
                else -> KategoriPresensi.AMAN
            }
        }
        hasilAbsensi.value = HasilAbsensi (absensi, kategori)
    }
    fun getHasilAbsensi() : LiveData<HasilAbsensi?> = hasilAbsensi
}