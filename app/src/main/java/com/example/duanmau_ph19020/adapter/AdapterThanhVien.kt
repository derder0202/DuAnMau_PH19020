package com.example.duanmau_ph19020.adapter
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.duanmau_ph19020.dao.TempFunc
import com.example.duanmau_ph19020.dao.TempFunc.Companion.removeDialog
import com.example.duanmau_ph19020.databinding.ItemThanhvienBinding
import com.example.duanmau_ph19020.fragments.QLTVFragment
import com.example.duanmau_ph19020.model.ThanhVien

class AdapterThanhVien(private val context: Context, private var fragment:QLTVFragment, private val list:ArrayList<ThanhVien>) : RecyclerView.Adapter<AdapterThanhVien.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemThanhvienBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val thanhVien = list[position]
        holder.maTV.text = "Mã thành viên: ${thanhVien.maTV}"
        holder.tenTV.text = thanhVien.hoTen
        holder.namSinh.text = thanhVien.namSinh
        holder.editBtn.setOnClickListener{
            fragment.openDialog(thanhVien,1)
        }
        holder.removeBtn.setOnClickListener{
            removeDialog(thanhVien,context,fragment)
        }
    }

    override fun getItemCount(): Int = list.size
    class ViewHolder(binding: ItemThanhvienBinding) : RecyclerView.ViewHolder(binding.root) {
        val maTV = binding.itemTvId
        val tenTV = binding.itemTvName
        val namSinh = binding.itemTvYear
        val editBtn = binding.itemTvEditBtn
        val removeBtn = binding.itemTvRemoveBtn
    }
}