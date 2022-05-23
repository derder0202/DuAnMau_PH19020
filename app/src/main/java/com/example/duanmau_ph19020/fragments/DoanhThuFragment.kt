package com.example.duanmau_ph19020.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.duanmau_ph19020.dao.PhieuMuonDAO
import com.example.duanmau_ph19020.databinding.FragmentDoanhThuBinding
import java.text.SimpleDateFormat
import java.util.*


class DoanhThuFragment : Fragment() {
    private var _binding: FragmentDoanhThuBinding? = null
    private val binding get() = _binding!!
    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDoanhThuBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.DoanhThuFragmentLayout.background.alpha = 130
        val tuNgay = binding.dtTuNgay
        val denNgay = binding.dtDenNgay
        datePickerDialogEdittext(tuNgay)
        datePickerDialogEdittext(denNgay)

        binding.dtTinhBtn.setOnClickListener {
            binding.dtResult.text = "Doanh thu: ${PhieuMuonDAO(requireContext()).getDoanhThu(tuNgay.text.toString(),denNgay.text.toString())}"
        }

        return root
    }

    @SuppressLint("SimpleDateFormat")
    fun datePickerDialogEdittext(editText: EditText){
        val cal = Calendar.getInstance()
        val listenerDatePickerDialog = DatePickerDialog.OnDateSetListener { _, i, i2, i3 ->
            cal.set(Calendar.YEAR,i)
            cal.set(Calendar.MONTH,i2)
            cal.set(Calendar.DAY_OF_MONTH,i3)
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            editText.setText(sdf.format(cal.time))
        }
        editText.setOnFocusChangeListener { _, b ->
            if(b){
                DatePickerDialog(requireContext(),listenerDatePickerDialog,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
                editText.setOnClickListener{
                    DatePickerDialog(requireContext(),listenerDatePickerDialog,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}