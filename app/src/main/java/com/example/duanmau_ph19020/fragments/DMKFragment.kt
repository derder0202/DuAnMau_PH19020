package com.example.duanmau_ph19020.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.duanmau_ph19020.dao.TempFunc
import com.example.duanmau_ph19020.dao.TempFunc.Companion.checkField
import com.example.duanmau_ph19020.dao.TempFunc.Companion.resetField
import com.example.duanmau_ph19020.dao.ThuThuDAO
import com.example.duanmau_ph19020.databinding.FragmentDmkBinding


class DMKFragment : Fragment() {
    private var _binding: FragmentDmkBinding? = null
    private val binding get() = _binding!!
    private lateinit var dao:ThuThuDAO
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDmkBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.DMKFragmentLayout.background.alpha = 130
        dao = ThuThuDAO(requireContext())
        val oldPass = binding.dmkOldPass
        val newPass = binding.dmkNewPass
        val rePass = binding.dmkNewPassRepeat
        binding.dmkSaveBtn.setOnClickListener {
            if(valiDateForm()){
                val preferences = requireActivity().getSharedPreferences("USER ACCOUNT",Context.MODE_PRIVATE)
                val thuThu = dao.getID(preferences.getString("username","").toString())
                thuThu.matKhau = newPass.editText!!.text.toString()
                Log.d("test pass",newPass.editText!!.text.toString())
                dao.update(thuThu)
                resetField(oldPass.editText!!,newPass.editText!!,rePass.editText!!)
            }
        }
        binding.dmkResetBtn.setOnClickListener {
            resetField(oldPass.editText!!,newPass.editText!!,rePass.editText!!)
        }
        return root
    }

    private fun valiDateForm():Boolean{
        if(checkField(binding.dmkOldPass,binding.dmkNewPass,binding.dmkNewPassRepeat)){
            val preferences = requireActivity().getSharedPreferences("USER ACCOUNT",Context.MODE_PRIVATE)!!
            val oldPass = preferences.getString("password","")
            val newPass = binding.dmkNewPass.editText!!.text.toString()
            val rePass = binding.dmkNewPassRepeat.editText!!.text.toString()
            if(!oldPass.equals(binding.dmkOldPass.editText!!.text.toString())){
                binding.dmkOldPass.error = "Mật khẩu cũ sai"
                return false
            } else binding.dmkOldPass.error = null
            if(newPass != rePass){
                binding.dmkNewPassRepeat.error = "Mật khẩu không trùng khớp"
                return false
            } else binding.dmkNewPassRepeat.error = null
            return true
        }
        return false
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}