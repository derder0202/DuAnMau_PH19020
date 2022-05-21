package com.example.duanmau_ph19020.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.duanmau_ph19020.adapter.AdapterPhieuMuon
import com.example.duanmau_ph19020.dao.PhieuMuonDAO
import com.example.duanmau_ph19020.dao.SachDAO
import com.example.duanmau_ph19020.dao.TempFunc.Companion.listOjectToString
import com.example.duanmau_ph19020.dao.ThanhVienDAO
import com.example.duanmau_ph19020.databinding.DialogPhieumuonBinding
import com.example.duanmau_ph19020.databinding.FragmentQlpmBinding
import com.example.duanmau_ph19020.model.PhieuMuon
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class QLPMFragment : Fragment() {

    private var _binding: FragmentQlpmBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterPhieuMuon: AdapterPhieuMuon
    private lateinit var list: ArrayList<PhieuMuon>
    private lateinit var dao: PhieuMuonDAO
    private var tempGia:Int=0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentQlpmBinding.inflate(inflater, container, false)
        val root: View = binding.root
        recyclerView = binding.qlpmRecylerView
        binding.qlpmFab.setOnClickListener {
            openDialog(PhieuMuon(),0)
        }
        updateRecylerView()
        return root
    }

    fun updateRecylerView(){
        dao = PhieuMuonDAO(requireContext())
        list = dao.getAll()
        adapterPhieuMuon = AdapterPhieuMuon(requireContext(),list,this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapterPhieuMuon
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    fun openDialog(phieuMuon: PhieuMuon, type:Int){
        dao = PhieuMuonDAO(requireContext())
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val builder = AlertDialog.Builder(requireContext())
        val binding = DialogPhieumuonBinding.inflate(layoutInflater)
        builder.setView(binding.root)
            .setTitle(if(type!=0)"Sửa thông tin phiếu mượn" else "Thêm phiếu mượn")
        val dialog = builder.create()
        dialog.show()
        val maPMTextInputLayout = binding.dialogPmMaPM
        maPMTextInputLayout.editText!!.isEnabled = false
        val spinnerTenTV = binding.dialogPmSpinnerTen
        val spinnerTenSach = binding.dialogPmSpinnerSach
        val ngayTv = binding.dialogPmNgay
        val tienThueTv = binding.dialogPmTienThue
        val traSachChk = binding.dialogPmCheckBox
        val thanhVienDAO = ThanhVienDAO(requireContext())
        val sachDAO = SachDAO(requireContext())

        val listThanhVien = thanhVienDAO.getAll()
        val listThanhVienToString = listOjectToString(listThanhVien)
        val adapterTV = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,listThanhVienToString)
        spinnerTenTV.adapter = adapterTV

        val listSach = sachDAO.getAll()
        val listSachToString = listOjectToString(listSach)
        val adapterSach = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,listSachToString)
        spinnerTenSach.adapter = adapterSach
        spinnerTenSach.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            @SuppressLint("SetTextI18n")
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val sach = listSach[p2]
                tienThueTv.text =  "Tiền thuê: ${sach.giaThue}"
                tempGia = sach.giaThue
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


        if(type!=0){
            maPMTextInputLayout.editText!!.setText(phieuMuon.maPM.toString())
            spinnerTenTV.setSelection(listThanhVien.indexOfFirst { thanhVien -> thanhVien.maTV == phieuMuon.maTV  })
            spinnerTenSach.setSelection(listSach.indexOfFirst { sach -> sach.maSach == phieuMuon.maSach })
            ngayTv.text = simpleDateFormat.format(phieuMuon.ngay)
            tienThueTv.text = phieuMuon.tienThue.toString()
            traSachChk.isSelected = phieuMuon.traSach != 0
        } else{
            ngayTv.text = "Ngày: ${simpleDateFormat.format(Calendar.getInstance().time)}"
        }

        binding.dialogPmCancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        binding.dialogPmSaveBtn.setOnClickListener {
            val msg = StringBuilder()
            if(listSach.isEmpty()){
                msg.append("Chưa có sách nào. Vui lòng thêm sách\n")
            }
            if(listThanhVien.isEmpty()){
                msg.append("Chưa có thành viên nào. Vui lòng thêm thành viên")
            }
            if (msg.isNotEmpty()){
                Toast.makeText(requireContext(),msg,Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

            phieuMuon.maSach = spinnerTenSach.selectedItem.toString().split("\t\t\t")[0].toInt()
            phieuMuon.maTV = spinnerTenTV.selectedItem.toString().split("\t\t\t")[0].toInt()
            phieuMuon.ngay = Calendar.getInstance().time
            phieuMuon.tienThue = tempGia
            phieuMuon.traSach = if(traSachChk.isChecked) 1 else 0

            if(type==0){
                dao.insert(phieuMuon)
            } else dao.update(phieuMuon)
            updateRecylerView()
            dialog.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}