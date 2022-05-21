package com.example.duanmau_ph19020.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.duanmau_ph19020.dao.TempFunc
import com.example.duanmau_ph19020.databinding.ItemSachBinding
import com.example.duanmau_ph19020.fragments.QLSFragment
import com.example.duanmau_ph19020.model.Sach

class AdapterSach (private var context: Context,private var list:ArrayList<Sach>,private var fragment:QLSFragment) : RecyclerView.Adapter<AdapterSach.ViewHolder>() {
    class ViewHolder(binding: ItemSachBinding) : RecyclerView.ViewHolder(binding.root) {
        val maSach = binding.itemQlsMaSach
        val tenSach = binding.itemQlsTenSach
        val giaThue = binding.itemQlsGiaThue
        val loaiSach = binding.itemQlsLoaiSach
        val editBtn = binding.itemQlsEditBtn
        val removeBtn = binding.itemQlsRemoveBtn
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSachBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sach = list[position]
        holder.maSach.text = "Mã sách: ${sach.maSach}"
        holder.tenSach.text = "Tên sách: ${sach.tenSach}"
        holder.giaThue.text = "Giá thuê: ${sach.giaThue}"
        holder.loaiSach.text = "Loại sách: ${sach.tenLoai}"
        holder.editBtn.setOnClickListener{
            fragment.openDialog(sach,1)
        }
        holder.removeBtn.setOnClickListener{
            TempFunc.removeDialog(sach,context,fragment)
        }
    }

    override fun getItemCount(): Int = list.size
}