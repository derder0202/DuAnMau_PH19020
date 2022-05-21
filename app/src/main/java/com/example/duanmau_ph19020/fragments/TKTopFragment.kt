package com.example.duanmau_ph19020.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.duanmau_ph19020.adapter.AdapterTop
import com.example.duanmau_ph19020.dao.PhieuMuonDAO
import com.example.duanmau_ph19020.databinding.FragmentTktopBinding
import com.example.duanmau_ph19020.model.TopTen


class TKTopFragment : Fragment() {
    private var _binding: FragmentTktopBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterTop: AdapterTop
    private lateinit var listTop:ArrayList<TopTen>
    private lateinit var phieuMuonDAO: PhieuMuonDAO

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTktopBinding.inflate(inflater, container, false)
        val root: View = binding.root
        recyclerView = binding.topRecylerView
        phieuMuonDAO = PhieuMuonDAO(requireContext())
        listTop = phieuMuonDAO.getTop()
        adapterTop = AdapterTop(requireContext(),listTop,this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapterTop
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}