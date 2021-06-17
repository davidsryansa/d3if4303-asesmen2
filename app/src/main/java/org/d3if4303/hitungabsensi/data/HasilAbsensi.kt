package org.d3if4303.hitungabsensi.data

data class HasilAbsensi(
    val absensi: Float,
    val kategori: KategoriPresensi
) {
    lateinit var value: HasilAbsensi
}
