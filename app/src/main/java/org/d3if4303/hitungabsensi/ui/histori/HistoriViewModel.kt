package org.d3if4303.hitungabsensi.ui.histori

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if4303.hitungabsensi.db.AbsensiDao

class HistoriViewModel(private val db: AbsensiDao) : ViewModel() {
    val data = db.getLastAbsen()

    fun hapusData() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            db.clearData()
        }
    }
}