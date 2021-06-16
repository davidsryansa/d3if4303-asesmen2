package org.d3if4303.hitungabsensi.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.d3if4303.hitungabsensi.R
import org.d3if4303.hitungabsensi.databinding.FragmentPerhitunganBinding

class PerhitunganFragment : Fragment() {

    private lateinit var binding: FragmentPerhitunganBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentPerhitunganBinding.inflate(layoutInflater, container, false)
        binding.button.setOnClickListener { hitungAbsensi() }
        return binding.root
    }

    private fun hitungAbsensi() {
        val kehadiran = binding.kehadiranEditText.text.toString()
        if (TextUtils.isEmpty(kehadiran)){
            Toast.makeText(context, R.string.kehadiran_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val pertemuan = binding.pertemuanEditText.text.toString()
        if (TextUtils.isEmpty(pertemuan)){
            Toast.makeText(context, R.string.pertemuan_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val selectedID = binding.radioGroup.checkedRadioButtonId
        if (selectedID == -1){
            Toast.makeText(context, R.string.kampus_invalid, Toast.LENGTH_LONG).show()
        }
        val mkuliah = selectedID == R.id.kampusTelkomRadioButton
        val absensi =  100 * (kehadiran.toFloat() / pertemuan.toFloat())
        val kategori = getKategori(absensi, mkuliah)

        binding.absensiTextView.text = getString(R.string.persentase_x, absensi)
        binding.kategoriTextView.text = getString(R.string.kategori_x, kategori)
    }

    private fun getKategori(absensi: Float, mkuliah: Boolean): String {
        val stringRes = if (mkuliah) {
            when {
                absensi < 75.0 -> R.string.tidakAman
                absensi >= 90.0 -> R.string.sangatAman
                else -> R.string.aman
            }
        } else {
            when {
                absensi < 70.0 -> R.string.tidakAman
                absensi >= 75.0 -> R.string.sangatAman
                else -> R.string.aman
            }
        }
        return getString(stringRes)
    }
}