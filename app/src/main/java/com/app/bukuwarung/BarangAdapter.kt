package com.app.bukuwarung

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.bukuwarung.room.Barang
import kotlinx.android.synthetic.main.list_barang.view.*

class BarangAdapter(private val barangs: ArrayList<Barang>, private val listener: OnAdapterListener) : RecyclerView.Adapter<BarangAdapter.BarangViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangViewHolder {
        return BarangViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_barang, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BarangViewHolder, position: Int) {
        val barang  = barangs[position]
        holder.view.text_nama.text = barang.nama
        holder.view.text_nama.setOnClickListener {
            listener.onClick(barang)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(barang)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(barang)
        }
    }

    override fun getItemCount() = barangs.size

    class BarangViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Barang>){
        barangs.clear()
        barangs.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener{
        fun onClick(barang: Barang)
        fun onUpdate(barang: Barang)
        fun onDelete(barang: Barang)
    }
}