package com.example.duanmau_ph19020.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.duanmau_ph19020.LoginActivity
import com.example.duanmau_ph19020.databinding.FragmentExitBinding

class ExitFragment : Fragment() {
    private var _binding: FragmentExitBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentExitBinding.inflate(inflater, container, false)
        val root: View = binding.root
        startActivity(Intent(requireActivity(),LoginActivity::class.java))
        Toast.makeText(requireContext(),"Đăng xuất thành công",Toast.LENGTH_SHORT).show()
        requireActivity().finish()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}