package org.d3if4303.hitungabsensi.ui.hitung

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.d3if4303.hitungabsensi.R
import org.d3if4303.hitungabsensi.data.KategoriPresensi
import org.d3if4303.hitungabsensi.databinding.FragmentPerhitunganBinding
import org.d3if4303.hitungabsensi.db.AbsensiDb

class PerhitunganFragment : Fragment() {

    private val viewModel: PerhitunganViewModel by lazy {
        val db = AbsensiDb.getInstance(requireContext())
        val factory = PerhitunganViewModelFactory(db.dao)
        ViewModelProvider(this, factory).get(PerhitunganViewModel::class.java)
    }

    private lateinit var binding: FragmentPerhitunganBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentPerhitunganBinding.inflate(layoutInflater, container, false)
        binding.button.setOnClickListener { hitungAbsensi() }
        binding.tanggapanButton.setOnClickListener { viewModel.mulaiNavigasi() }
        binding.shareButton.setOnClickListener { shareData() }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_histori -> {
                findNavController().navigate(R.id.action_perhitunganFragment_to_historiFragment)
                return true
            }
            R.id.menu_about -> {
                findNavController().navigate(R.id.action_perhitunganFragment_to_aboutFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getNavigasi().observe(viewLifecycleOwner, {
            if (it == null) return@observe
            findNavController().navigate(PerhitunganFragmentDirections
                .actionPerhitunganFragmentToTanggapanFragment(it))
            viewModel.selesaiNavigasi()
        })

        viewModel.getHasilAbsensi().observe(viewLifecycleOwner, {
            if (it == null) return@observe
            binding.absensiTextView.text = getString(R.string.persentase_x, it.absensi)
            binding.kategoriTextView.text = getString(R.string.kategori_x,
                getKategori(it.kategori))
            binding.buttonGroup.visibility = View.VISIBLE
        })

        viewModel.data.observe(viewLifecycleOwner, {
            if (it == null) return@observe
            Log.d("PerhitunganFragment", "Data Tersimpan. ID = ${it.id}")
        })
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

        viewModel.hitungAbsensi(kehadiran, pertemuan, mkuliah)
    }

    private fun getKategori(kategori: KategoriPresensi): String {
        val stringRes = when (kategori) {
            KategoriPresensi.TIDAKAMAN -> R.string.tidakAman
            KategoriPresensi.SANGATAMAN -> R.string.sangatAman
            KategoriPresensi.AMAN -> R.string.aman
        }
        return getString(stringRes)
    }

    private fun shareData() {
        val selectedId = binding.radioGroup.checkedRadioButtonId
        val keter = if (selectedId == R.id.kampusTelkomRadioButton)
            getString(R.string.telkom)
        else
            getString(R.string.laen)
        val message = getString(R.string.template_bagikan,
            binding.kehadiranEditText.text,
            binding.pertemuanEditText.text,
            keter,
            binding.absensiTextView.text,
            binding.kategoriTextView.text )
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity( requireActivity().packageManager) != null) {
            startActivity(shareIntent)
        }
    }
}