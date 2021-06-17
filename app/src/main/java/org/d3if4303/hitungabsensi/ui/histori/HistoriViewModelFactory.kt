package org.d3if4303.hitungabsensi.ui.histori

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if4303.hitungabsensi.db.AbsensiDao

class HistoriViewModelFactory (
    private val db: AbsensiDao
) : ViewModelProvider.Factory {
        @Suppress ("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HistoriViewModel::class.java)) {
                return HistoriViewModel(db) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}