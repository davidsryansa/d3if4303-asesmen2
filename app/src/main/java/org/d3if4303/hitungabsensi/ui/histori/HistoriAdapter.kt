package org.d3if4303.hitungabsensi.ui.histori

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.d3if4303.hitungabsensi.R
import org.d3if4303.hitungabsensi.data.HitungAbsensi
import org.d3if4303.hitungabsensi.databinding.ItemHistoriBinding
import org.d3if4303.hitungabsensi.db.AbsensiEntity
import java.text.SimpleDateFormat
import java.util.*

class HistoriAdapter : RecyclerView.Adapter<HistoriAdapter.ViewHolder>() {

    private val data = mutableListOf<AbsensiEntity>()

    fun updateData(newData: List<AbsensiEntity>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoriBinding.inflate(
            inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(
        private val binding: ItemHistoriBinding
        ) : RecyclerView.ViewHolder(binding.root) {

        private val dateFormatter = SimpleDateFormat(
            "dd MMMM yyyy",
            Locale("id", "ID")
        )

        fun bind(item: AbsensiEntity) = with(binding) {
            tanggalTextView.text = dateFormatter.format(Date(item.tanggal))

            val hasilAbsensi = HitungAbsensi.hitung(item)
            kategoriTextView.text = hasilAbsensi.kategori.toString()
            absensiTextView.text = root.context.getString(
                R.string.persentase_x, hasilAbsensi.absensi
            )

            kehadiranTextView.text = root.context.getString(
                R.string.kehadiran_x, item.kehadiran
            )
            pertemuanTextView.text = root.context.getString(
                R.string.pertemuan_x,
                item.pertemuan
            )
            val stringRes = if (item.mkuliah) R.string.telkom else R.string.laen
            kampusTextView.text = root.context.getString(stringRes)
        }

    }
}