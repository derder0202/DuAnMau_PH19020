package com.example.duanmau_ph19020.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.duanmau_ph19020.dao.SachDAO
import com.example.duanmau_ph19020.dao.TempFunc.Companion.removeDialog
import com.example.duanmau_ph19020.databinding.ItemLoaisachBinding
import com.example.duanmau_ph19020.fragments.QLLSFragment
import com.example.duanmau_ph19020.model.LoaiSach

class AdapterLoaiSach(private val context: Context, private var list: ArrayList<LoaiSach>, private val fragment:QLLSFragment) : RecyclerView.Adapter<AdapterLoaiSach.ViewHolder>(),Filterable {
    val oldList = list
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
            if(SachDAO(context).getAll().size!=0){
                Toast.makeText(context,"Không thể xóa loại sách khi có sách trong loại sách này",Toast.LENGTH_SHORT).show()
            } else removeDialog(loaiSach,context,fragment)
        }
        holder.editBtn.setOnClickListener{
            fragment.openDialog(loaiSach,1)
        }
    }

    override fun getItemCount(): Int = list.size
    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val strSearch = p0.toString()
                if(strSearch.isEmpty()){
                    list = oldList
                } else{
                    list = oldList.filter { loaiSach -> loaiSach.tenLoai.lowercase().contains(strSearch.lowercase()) } as ArrayList<LoaiSach>
                }
                val filterResult = FilterResults()
                filterResult.values = list
                return filterResult
            }
            override fun publishResults(p0: CharSequence?, p1: FilterResults) {
                list = p1.values as ArrayList<LoaiSach>
                notifyDataSetChanged()
            }

        }
    }
}