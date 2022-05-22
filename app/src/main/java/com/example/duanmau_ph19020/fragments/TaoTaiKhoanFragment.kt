package com.example.duanmau_ph19020.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.duanmau_ph19020.LoginActivity
import com.example.duanmau_ph19020.R
import com.example.duanmau_ph19020.databinding.ActivityMainBinding
import com.example.duanmau_ph19020.databinding.FragmentExitBinding
import com.example.duanmau_ph19020.databinding.FragmentTaoTaiKhoanBinding
import com.google.android.material.navigation.NavigationView


class TaoTaiKhoanFragment : Fragment() {
    private var _binding: FragmentTaoTaiKhoanBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTaoTaiKhoanBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val username = requireActivity().intent.getStringExtra("user")
        if(username!="admin"){
            Toast.makeText(requireContext(),"Bạn không phải admin",Toast.LENGTH_SHORT).show()
            val navView = requireActivity().findViewById<NavigationView>(R.id.nav_view)
            navView.menu.getItem(0).isChecked = true
            (activity as AppCompatActivity).supportActionBar?.title = "Quản lý Phiếu Mượn"
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.ttkFragment,QLPMFragment()).commit()
        } else{

        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}