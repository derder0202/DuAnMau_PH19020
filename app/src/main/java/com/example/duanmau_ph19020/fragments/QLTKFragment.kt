package com.example.duanmau_ph19020.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.duanmau_ph19020.adapter.AdapterTaiKhoan
import com.example.duanmau_ph19020.dao.TempFunc.Companion.checkField
import com.example.duanmau_ph19020.dao.ThuThuDAO
import com.example.duanmau_ph19020.databinding.DialogQltkBinding
import com.example.duanmau_ph19020.databinding.FragmentQltkBinding
import com.example.duanmau_ph19020.model.ThuThu



class QLTKFragment : Fragment() {
    private var _binding: FragmentQltkBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var thuThuDAO: ThuThuDAO
    private lateinit var adapterTaiKhoan: AdapterTaiKhoan
    private lateinit var listTK:ArrayList<ThuThu>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentQltkBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.ttkFragmentLayout.background.alpha = 130
        recyclerView = binding.qltkRecylerView
        binding.qltkFab.setOnClickListener {
            openDialog(ThuThu(),0)
        }
        updateRecylerView()
        return root
    }

    fun updateRecylerView(){
        thuThuDAO = ThuThuDAO(requireContext())
        listTK = thuThuDAO.getAll()
        adapterTaiKhoan = AdapterTaiKhoan(requireContext(),listTK,this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapterTaiKhoan
    }

    fun openDialog(thuThu: ThuThu,type:Int){
        val builer = AlertDialog.Builder(requireContext())
        val binding = DialogQltkBinding.inflate(layoutInflater)
        builer.setView(binding.root)
            .setTitle(if(type!=0)"Sửa thông tin tài khoản" else "Thêm tài khoản")
        val dialog = builer.create()
        dialog.show()

        val username = binding.dialogQltkUsername
        val hoTen = binding.dialogQltkHoTen
        val password = binding.dialogQltkPassword
        val rePassword = binding.dialogQltkRePassword

        if(type!=0){
            username.editText!!.setText(thuThu.maTT)
            hoTen.editText!!.setText(thuThu.hoTen)
            username.editText!!.isEnabled = false
        }

        binding.dialogQltkSaveBtn.setOnClickListener {
            checkField(username)
            checkField(hoTen)
            checkField(password)
            checkField(rePassword)
            if(checkField(username)&&checkField(hoTen)&& checkField(password)&& checkField(rePassword)){
                if(!valiDatePassword(password.editText!!.text.toString(),rePassword.editText!!.text.toString())){
                    rePassword.error = "Mật khẩu nhập lại không trùng khớp"
                } else{
                    thuThu.hoTen = hoTen.editText!!.text.toString()
                    thuThu.matKhau = password.editText!!.text.toString()
                    if(type!=0){
                        thuThuDAO.update(thuThu)
                    } else{
                        thuThu.maTT = username.editText!!.text.toString()
                        thuThuDAO.insert(thuThu)
                    }
                    dialog.dismiss()
                    updateRecylerView()
                }
            }
        }
        binding.dialogQltkCancelBtn.setOnClickListener {
            dialog.dismiss()
        }

    }

    fun valiDatePassword(password1:String,password2:String) = password1==password2

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}