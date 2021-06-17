package org.d3if4303.hitungabsensi.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import org.d3if4303.hitungabsensi.R
import org.d3if4303.hitungabsensi.data.KategoriPresensi
import org.d3if4303.hitungabsensi.databinding.FragmentPerhitunganBinding

class PerhitunganFragment : Fragment() {

    private lateinit var binding: FragmentPerhitunganBinding
    private lateinit var kategoriPresensi: KategoriPresensi

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentPerhitunganBinding.inflate(layoutInflater, container, false)
        binding.button.setOnClickListener { hitungAbsensi() }
        binding.tanggapanButton.setOnClickListener { view: View ->
            view.findNavController().navigate(
                PerhitunganFragmentDirections
                    .actionPerhitunganFragmentToTanggapanFragment(kategoriPresensi)
            )
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_about) {
            findNavController().navigate(
                R.id.action_perhitunganFragment_to_aboutFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
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
        binding.tanggapanButton.visibility = View.VISIBLE
    }

    private fun getKategori(absensi: Float, mkuliah: Boolean): String {
        kategoriPresensi = if (mkuliah) {
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
        val stringRes = when (kategoriPresensi) {
            KategoriPresensi.TIDAKAMAN -> R.string.tidakAman
            KategoriPresensi.SANGATAMAN -> R.string.sangatAman
            KategoriPresensi.AMAN -> R.string.aman
        }
        return getString(stringRes)
    }
}