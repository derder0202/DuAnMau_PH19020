package com.example.duanmau_ph19020.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.duanmau_ph19020.databinding.ItemTopBinding
import com.example.duanmau_ph19020.fragments.TKTopFragment
import com.example.duanmau_ph19020.model.TopTen

class AdapterTop (private val context: Context,private val list: ArrayList<TopTen>,private val fragment:TKTopFragment) : RecyclerView.Adapter<AdapterTop.ViewHolder>() {
    class ViewHolder(binding:ItemTopBinding) : RecyclerView.ViewHolder(binding.root) {
        val tenSach = binding.itemTopTenSach
        val soLuong = binding.itemTopSoLuong
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTopBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val top = list[position]
        holder.tenSach.text = "Sách: ${top.tenSach}"
        holder.soLuong.text = "Số Lượng: ${top.soLuong}"
    }

    override fun getItemCount(): Int = list.size
}