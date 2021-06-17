package org.d3if4303.hitungabsensi.ui.histori

import androidx.lifecycle.ViewModel
import org.d3if4303.hitungabsensi.db.AbsensiDao

class HistoriViewModel(db: AbsensiDao) : ViewModel() {
    val data = db.getLastAbsen()
}