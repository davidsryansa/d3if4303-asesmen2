package org.d3if4303.hitungabsensi.ui

import android.app.AppComponentFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import org.d3if4303.hitungabsensi.R
import org.d3if4303.hitungabsensi.data.KategoriPresensi
import org.d3if4303.hitungabsensi.databinding.FragmentTanggapanBinding

class TanggapanFragment : Fragment(){

    private val args:  TanggapanFragmentArgs by navArgs()
    private lateinit var binding: FragmentTanggapanBinding

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,
                               savedInstanceState: Bundle?): View? {
        binding = FragmentTanggapanBinding.inflate(layoutInflater, container, false)
        updateUI(args.kategori)
        return binding.root
    }

    private fun updateUI (kategori: KategoriPresensi) {
        val actionBar = (requireActivity() as AppCompatActivity)
            .supportActionBar
        when (kategori) {
            KategoriPresensi.SANGATAMAN -> {
                actionBar?.title = getString(R.string.t_sangat_aman)
                binding.imageView.setImageResource(R.drawable.nice)
                binding.textView.text = getString(R.string.tb_sangat_aman)
            }
            KategoriPresensi.AMAN -> {
                actionBar?.title = getString(R.string.t_aman)
                binding.imageView.setImageResource(R.drawable.notbad)
                binding.textView.text = getString(R.string.tb_aman)
            }
            KategoriPresensi.TIDAKAMAN -> {
                actionBar?.title = getString(R.string.t_tidak_aman)
                binding.imageView.setImageResource(R.drawable.surrender)
                binding.textView.text = getString(R.string.tb_tidak_aman)
            }
        }
    }

}