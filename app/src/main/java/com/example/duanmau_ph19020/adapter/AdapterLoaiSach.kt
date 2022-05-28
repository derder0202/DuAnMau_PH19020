package com.example.duanmau_ph19020.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.duanmau_ph19020.dao.TempFunc.Companion.removeDialog
import com.example.duanmau_ph19020.databinding.ItemLoaisachBinding
import com.example.duanmau_ph19020.fragments.QLLSFragment
import com.example.duanmau_ph19020.model.LoaiSach

class AdapterLoaiSach(private val context: Context, private val list: ArrayList<LoaiSach>, private val fragment:QLLSFragment) : RecyclerView.Adapter<AdapterLoaiSach.ViewHolder>() {
    class ViewHolder(binding: ItemLoaisachBinding) : RecyclerView.ViewHolder(binding.root) {
        val maLoai = binding.itemQllsMaLoai
        val tenloai = binding.itemQllsTenLoai
        val removeBtn = binding.itemQllsRemoveBtn
        val editBtn = binding.itemQllsEditBtn
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLoaisachBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val loaiSach = list[position]
        holder.maLoai.text = "Mã loại: ${loaiSach.maLoai} "
        holder.tenloai.text = loaiSach.tenLoai
        holder.removeBtn.setOnClickListener{
            removeDialog(loaiSach,context,fragment)
        }
        holder.editBtn.setOnClickListener{
            fragment.openDialog(loaiSach,1)
        }
    }

    override fun getItemCount(): Int = list.size
}