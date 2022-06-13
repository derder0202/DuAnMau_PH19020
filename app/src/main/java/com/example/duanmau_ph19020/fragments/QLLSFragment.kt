package com.example.duanmau_ph19020.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.duanmau_ph19020.R
import com.example.duanmau_ph19020.adapter.AdapterLoaiSach
import com.example.duanmau_ph19020.dao.LoaiSachDAO
import com.example.duanmau_ph19020.dao.TempFunc
import com.example.duanmau_ph19020.dao.TempFunc.Companion.checkField
import com.example.duanmau_ph19020.databinding.DialogQllsBinding
import com.example.duanmau_ph19020.databinding.FragmentQllsBinding
import com.example.duanmau_ph19020.model.LoaiSach
import java.util.*
import kotlin.collections.ArrayList

class QLLSFragment : Fragment() {

    private var _binding: FragmentQllsBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView:RecyclerView
    private lateinit var dao:LoaiSachDAO
    private lateinit var adapterLoaisach: AdapterLoaiSach
    private lateinit var list: ArrayList<LoaiSach>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQllsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setHasOptionsMenu(true)
        binding.QLLSFragmentLayout.background.alpha = 130
        recyclerView = binding.qllsRecylerView
        dao = LoaiSachDAO(requireContext())
        binding.qllsFab.setOnClickListener{
            openDialog(LoaiSach(),0)
        }

        updateRecylerView()

        return root
    }

    fun updateRecylerView(){
        dao = LoaiSachDAO(requireContext())
        list = dao.getAll()
        adapterLoaisach = AdapterLoaiSach(requireContext(),list,this)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapterLoaisach
        recyclerView.layoutManager = linearLayoutManager
    }
    fun openDialog(loaiSach: LoaiSach,type:Int){
        val builder = AlertDialog.Builder(requireContext())
        val binding = DialogQllsBinding.inflate(layoutInflater)
        builder.setTitle(if(type==0)"Thêm loại sách" else "Sửa loại sách")
            .setView(binding.root)
        val alertDialog = builder.create()
        alertDialog.show()

        val maLoai = binding.dialogQllsMaLoai
        val tenLoai = binding.dialogQllsTenLoai

        maLoai.editText!!.isEnabled = false
        tenLoai.editText!!.requestFocus()
        if (type!=0){
            maLoai.editText!!.setText(loaiSach.maLoai.toString())
            tenLoai.editText!!.setText(loaiSach.tenLoai)
        }

        binding.dialogQllsSaveBtn.setOnClickListener {
            if(checkField(tenLoai)){
                loaiSach.tenLoai = tenLoai.editText!!.text.toString()
                if(type==0){
                    dao.insert(loaiSach)
                } else{
                    dao.update(loaiSach)
                }
                updateRecylerView()
                alertDialog.dismiss()
            }
        }
        binding.dialogQllsCancelBtn.setOnClickListener {
            alertDialog.dismiss()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main,menu)
        super.onCreateOptionsMenu(menu, inflater)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapterLoaisach.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterLoaisach.filter.filter(newText)
                return false
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}