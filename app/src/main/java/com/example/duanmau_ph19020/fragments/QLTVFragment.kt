package com.example.duanmau_ph19020.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.duanmau_ph19020.adapter.AdapterThanhVien
import com.example.duanmau_ph19020.dao.*
import com.example.duanmau_ph19020.dao.TempFunc.Companion.checkField
import com.example.duanmau_ph19020.databinding.DialogThanhvienBinding
import com.example.duanmau_ph19020.databinding.FragmentQltvBinding
import com.example.duanmau_ph19020.model.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class QLTVFragment : Fragment() {
    private lateinit var thanhVienDAO:ThanhVienDAO
    private var _binding: FragmentQltvBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterThanhvien: AdapterThanhVien
    private lateinit var listThanhVien: ArrayList<ThanhVien>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentQltvBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.QLTVFragmentLayout.background.alpha = 130
        thanhVienDAO = ThanhVienDAO(requireContext())

        binding.qltvFab.setOnClickListener{
            openDialog(ThanhVien(),0)
        }
        updateRecylerView()

        return root
    }

    fun updateRecylerView(){
        thanhVienDAO = ThanhVienDAO(requireContext())
        listThanhVien = thanhVienDAO.getAll()
        adapterThanhvien = AdapterThanhVien(requireContext(),this,listThanhVien)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.qltvRecylerView.layoutManager = linearLayoutManager
        binding.qltvRecylerView.adapter = adapterThanhvien
    }

//    fun removeDialog(thanhVien: ThanhVien,dao:ThanhVienDAO){
//        val builder = AlertDialog.Builder(requireActivity())
//            .setTitle("Xoa Thanh Vien")
//            .setMessage("Ban co muon xoa khong")
//            .setCancelable(true)
//            .setPositiveButton("YES") { dialogInterface, i ->
//                thanhVienDAO.remove(thanhVien)
//                updateRecylerView()
//                dialogInterface.dismiss()
//            }
//            .setNegativeButton("NO") { dialogInterface, i ->
//                dialogInterface.dismiss()
//            }
//        val dialog = builder.create()
//        dialog.show()
//    }

    @SuppressLint("SimpleDateFormat")
    fun openDialog(thanhVien: ThanhVien, type:Int){
        val builder = AlertDialog.Builder(requireActivity())
        val binding = DialogThanhvienBinding.inflate(layoutInflater)
        builder.setView(binding.root)
            .setTitle(if(type!=0)"Sửa thông tin thành viên" else "Thêm thành viên")
        val alertDialog = builder.create()
        alertDialog.show()
        val maTV = binding.dialogTvMaTV
        val hoTen = binding.dialogTvHoTen
        val ngaySinh = binding.dialogTvNgaySinh
        hoTen.editText!!.requestFocus()
        if(type!=0){
            hoTen.editText!!.setText(thanhVien.hoTen)
            ngaySinh.editText!!.setText(thanhVien.namSinh)
            maTV.editText!!.setText(thanhVien.maTV.toString())
        }
        val cal = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, i, i2, i3 ->
            cal.set(Calendar.YEAR,i)
            cal.set(Calendar.MONTH,i2)
            cal.set(Calendar.DAY_OF_MONTH,i3)
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            ngaySinh.editText!!.setText(sdf.format(cal.time))
        }
        ngaySinh.editText!!.setOnFocusChangeListener{ _, b ->
            if(b){
                DatePickerDialog(requireContext(),dateSetListener,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
                ngaySinh.editText!!.setOnClickListener{
                    DatePickerDialog(requireContext(),dateSetListener,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
                }
            }
        }

        binding.dialogTvSaveBtn.setOnClickListener {
            if(checkField(hoTen,ngaySinh)){
                thanhVien.hoTen = hoTen.editText!!.text.toString()
                thanhVien.namSinh = ngaySinh.editText!!.text.toString()
                if(type==0){
                    thanhVienDAO.insert(thanhVien)
                }
                else {
                    thanhVienDAO.update(thanhVien)
                }
                updateRecylerView()
                alertDialog.dismiss()
            }
        }
        binding.dialogTvResetBtn.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}