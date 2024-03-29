package com.example.duanmau_ph19020.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.duanmau_ph19020.R
import com.example.duanmau_ph19020.adapter.AdapterSach
import com.example.duanmau_ph19020.dao.LoaiSachDAO
import com.example.duanmau_ph19020.dao.SachDAO
import com.example.duanmau_ph19020.dao.TempFunc.Companion.checkField
import com.example.duanmau_ph19020.dao.TempFunc.Companion.checkNumber
import com.example.duanmau_ph19020.databinding.DialogSachBinding
import com.example.duanmau_ph19020.databinding.FragmentQlsBinding
import com.example.duanmau_ph19020.model.Sach

class QLSFragment : Fragment() {

    private var _binding: FragmentQlsBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView:RecyclerView
    private lateinit var dao: SachDAO
    private lateinit var list:ArrayList<Sach>
    private lateinit var adapterSach: AdapterSach

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentQlsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        val root: View = binding.root
        binding.QLSFragmentLayout.background.alpha = 130
        recyclerView = binding.qlsRecylerView
        dao = SachDAO(requireContext())
        binding.qlsFab.setOnClickListener{
            openDialog(Sach(),0)
        }
        updateRecylerView()
        return root
    }

    fun openDialog(sach: Sach,type:Int){
        val builder = AlertDialog.Builder(requireContext())
        val binding = DialogSachBinding.inflate(layoutInflater)
        builder.setView(binding.root)
            .setTitle(if(type==0)"Thêm sách" else "Sửa thông tin sách")
        val dialog = builder.create()
        dialog.show()
        val maSach = binding.dialogQlsMaSach
        val tenSach = binding.dialogQlsTenSach
        val giaThue = binding.dialogQlsGiaThue
        val spinner = binding.dialogQlsSpinner
        maSach.editText!!.isEnabled = false
        tenSach.editText!!.requestFocus()

        val list = ArrayList<String>()
        val listLoaiSach = LoaiSachDAO(requireContext()).getAll()
        for(loaiSach in listLoaiSach){
            list.add("${loaiSach.maLoai}\t\t${loaiSach.tenLoai}")
        }
        val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,list)
        spinner.adapter = adapter

        if(type!=0){
            maSach.editText!!.setText(sach.maSach.toString())
            tenSach.editText!!.setText(sach.tenSach)
            giaThue.editText!!.setText(sach.giaThue.toString())
            spinner.setSelection(listLoaiSach.indexOfFirst { loaiSach -> loaiSach.maLoai == sach.maLoai })
        }

        binding.dialogQlsCancelBtn.setOnClickListener { dialog.dismiss() }
        binding.dialogQlsSaveBtn.setOnClickListener {
//            checkField(tenSach)
//            checkField(giaThue)
            if (listLoaiSach.isEmpty()){
                Toast.makeText(requireContext(),"Bạn chưa nhập loại sách nào\nVui lòng thêm loại sách trước",Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else{
                if(checkField(tenSach,giaThue) && checkNumber(giaThue)){
                    sach.tenSach = tenSach.editText!!.text.toString()
                    sach.giaThue = giaThue.editText!!.text.toString().toInt()
                    sach.maLoai = spinner.selectedItem.toString().split("\t\t")[0].toInt()
                    if(type==0){
                        dao.insert(sach)
                    } else dao.update(sach)
                    dialog.dismiss()
                }
                updateRecylerView()
            }
        }
    }

    fun updateRecylerView(){
        dao = SachDAO(requireContext())
        list = dao.getAll()
        adapterSach = AdapterSach(requireContext(),list,this)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapterSach
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main,menu)
        super.onCreateOptionsMenu(menu, inflater)
        val searchView = menu.findItem(R.id.action_search).actionView as androidx.appcompat.widget.SearchView
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener
             {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                adapterSach.filter.filter(p0)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapterSach.filter.filter(p0)
                return false
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}