package org.d3if4303.hitungabsensi.ui.hitung

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if4303.hitungabsensi.data.HasilAbsensi
import org.d3if4303.hitungabsensi.data.HitungAbsensi
import org.d3if4303.hitungabsensi.data.KategoriPresensi
import org.d3if4303.hitungabsensi.db.AbsensiDao
import org.d3if4303.hitungabsensi.db.AbsensiEntity

class PerhitunganViewModel(private val db: AbsensiDao): ViewModel() {

    //Hasil Absensi bisa null jika pengguna belum menghitung Absensi
    private val hasilAbsensi = MutableLiveData<HasilAbsensi?>()

    // Navigasi akan bernilai null ketika tidak bernavigasi
    private val navigasi = MutableLiveData<KategoriPresensi>()

    fun hitungAbsensi(kehadiran: String, pertemuan: String, mkuliah: Boolean){
        val dataAbsensi = AbsensiEntity(
            kehadiran = kehadiran.toFloat(),
            pertemuan = pertemuan.toFloat(),
            mkuliah = mkuliah
        )

        hasilAbsensi.value = HitungAbsensi.hitung(dataAbsensi)

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.insert(dataAbsensi)
            }
        }
    }

    fun mulaiNavigasi() {
        navigasi.value = hasilAbsensi.value?.kategori
    }

    fun selesaiNavigasi() {
        navigasi.value = null
    }

    fun getHasilAbsensi() : LiveData<HasilAbsensi?> = hasilAbsensi

    fun getNavigasi(): LiveData<KategoriPresensi> = navigasi
}