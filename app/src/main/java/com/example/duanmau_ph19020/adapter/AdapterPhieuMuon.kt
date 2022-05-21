package com.example.duanmau_ph19020.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.duanmau_ph19020.dao.SachDAO
import com.example.duanmau_ph19020.dao.TempFunc
import com.example.duanmau_ph19020.dao.ThanhVienDAO
import com.example.duanmau_ph19020.databinding.ItemPhieumuonBinding
import com.example.duanmau_ph19020.fragments.QLPMFragment
import com.example.duanmau_ph19020.model.PhieuMuon
import java.text.SimpleDateFormat

class AdapterPhieuMuon(private val context: Context,private val list:ArrayList<PhieuMuon>,private val fragment:QLPMFragment) : RecyclerView.Adapter<AdapterPhieuMuon.ViewHolder>() {
    class ViewHolder(binding:ItemPhieumuonBinding) : RecyclerView.ViewHolder(binding.root) {
        val maPM = binding.itemPmMaPM
        val tenTV = binding.itemPmTenTV
        val tenSach = binding.itemPmTenSach
        val tienThue = binding.itemPmTienThue
        val traSach = binding.itemPmTraSach
        val ngayThue = binding.itemPmNgayThue
        val editBtn = binding.itemPmEditBtn
        val removeBtn = binding.itemPmRemoveBtn
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPhieumuonBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val phieuMuon = list[position]
        val listThanhVien = ThanhVienDAO(context).getAll()
        val listSach = SachDAO(context).getAll()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

        holder.maPM.text = "Mã phiếu: ${phieuMuon.maPM}"
        holder.tenTV.text = "Thành viên: ${listThanhVien[listThanhVien.indexOfFirst { thanhVien -> thanhVien.maTV == phieuMuon.maTV }].hoTen}"
        holder.tenSach.text = "Tên sách: ${listSach[listSach.indexOfFirst { sach -> sach.maSach == phieuMuon.maSach  }].tenSach}"
        holder.tienThue.text = "Tiền thuê: ${phieuMuon.tienThue}"
        holder.traSach.text = if(phieuMuon.traSach==1) "Đã trả sách" else "Chưa trả sách"
        if(phieuMuon.traSach==0) holder.traSach.setTextColor(Color.RED)
        holder.ngayThue.text = "Ngày thuê: ${simpleDateFormat.format(phieuMuon.ngay)}"

        holder.removeBtn.setOnClickListener {
            TempFunc.removeDialog(phieuMuon,context,fragment)
        }
        holder.editBtn.setOnClickListener {
            fragment.openDialog(phieuMuon,1)
        }
    }

    override fun getItemCount(): Int = list.size
}