package org.d3if4303.hitungabsensi.ui.hitung

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if4303.hitungabsensi.db.AbsensiDao

class PerhitunganViewModelFactory (
    private val db: AbsensiDao
) : ViewModelProvider.Factory {
        @Suppress ("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PerhitunganViewModel::class.java)) {
                return PerhitunganViewModel(db) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}