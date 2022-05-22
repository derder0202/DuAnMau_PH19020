package com.example.duanmau_ph19020.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.duanmau_ph19020.dao.TempFunc
import com.example.duanmau_ph19020.databinding.ItemLoaisachBinding
import com.example.duanmau_ph19020.fragments.TaoTaiKhoanFragment
import com.example.duanmau_ph19020.model.ThuThu

class AdapterTaiKhoan(private var context: Context,private var list:ArrayList<ThuThu>,private var fragment:TaoTaiKhoanFragment) : RecyclerView.Adapter<AdapterTaiKhoan.ViewHolder>() {
    class ViewHolder(binding: ItemLoaisachBinding) : RecyclerView.ViewHolder(binding.root) {
        var username = binding.itemQllsMaLoai
        var password = binding.itemQllsTenLoai
        var editBtn = binding.itemQllsEditBtn
        var removeBtn = binding.itemQllsRemoveBtn
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLoaisachBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val thuThu = list[position]
        holder.username.text = "Tên đăng nhập: ${thuThu.maTT}"
        holder.password.text = "Mật khẩu: ${thuThu.matKhau}"
        holder.password.textSize = 14F
        
        holder.removeBtn.setOnClickListener {
            if(holder.username.text.toString()=="admin"){
                Toast.makeText(context,"Bạn không thể xóa admin",Toast.LENGTH_SHORT).show()
            } else{
                TempFunc.removeDialog(thuThu,context,fragment)
            }
        }
        holder.editBtn.setOnClickListener {
            fragment.openDialog(thuThu,1)
        }
    }

    override fun getItemCount(): Int = list.size
}