package com.example.duanmau_ph19020.adapter
import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.duanmau_ph19020.R
import com.example.duanmau_ph19020.dao.PhieuMuonDAO
import com.example.duanmau_ph19020.dao.TempFunc
import com.example.duanmau_ph19020.dao.TempFunc.Companion.removeDialog
import com.example.duanmau_ph19020.databinding.ItemThanhvienBinding
import com.example.duanmau_ph19020.fragments.QLTVFragment
import com.example.duanmau_ph19020.model.ThanhVien

class AdapterThanhVien(private val context: Context, private var fragment:QLTVFragment, private var list:ArrayList<ThanhVien>) : RecyclerView.Adapter<AdapterThanhVien.ViewHolder>(),Filterable {

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
            if(PhieuMuonDAO(context).getAll().indexOfFirst { phieuMuon -> phieuMuon.maTV == thanhVien.maTV } != -1){
                Toast.makeText(context,"Không thể xóa thành viên. Hiện đang có phiếu mượn mà thành viên mượn",Toast.LENGTH_SHORT).show()
            } else removeDialog(thanhVien,context,fragment)
        }

        if (thanhVien.img=="null"){
            holder.img.load(R.drawable.unknown)
        } else{
            holder.img.load(Uri.parse(thanhVien.img))
        }
    }

    override fun getItemCount(): Int = list.size
    class ViewHolder(binding: ItemThanhvienBinding) : RecyclerView.ViewHolder(binding.root) {
        val maTV = binding.itemTvId
        val tenTV = binding.itemTvName
        val namSinh = binding.itemTvYear
        val editBtn = binding.itemTvEditBtn
        val removeBtn = binding.itemTvRemoveBtn
        val img = binding.itemTvImg
    }
    val oldList = list
    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val searchValue = p0.toString()
                if(searchValue.isEmpty()){
                    list = oldList
                } else{
                    list = oldList.filter { thanhVien -> thanhVien.hoTen.lowercase().contains(searchValue.lowercase()) } as ArrayList<ThanhVien>
                }
                val result = FilterResults()
                result.values = list
                return result
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults) {
                list = p1.values as ArrayList<ThanhVien>
                notifyDataSetChanged()
            }

        }
    }
}