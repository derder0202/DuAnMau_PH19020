package com.example.duanmau_ph19020.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.duanmau_ph19020.R
import com.example.duanmau_ph19020.adapter.AdapterThanhVien
import com.example.duanmau_ph19020.dao.*
import com.example.duanmau_ph19020.dao.TempFunc.Companion.checkField
import com.example.duanmau_ph19020.databinding.DialogThanhvienBinding
import com.example.duanmau_ph19020.databinding.FragmentQltvBinding
import com.example.duanmau_ph19020.model.*
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class QLTVFragment : Fragment() {
    lateinit var imgView:ImageView
    private  var uri: Uri? = null
    private lateinit var thanhVienDAO:ThanhVienDAO
    private var _binding: FragmentQltvBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterThanhvien: AdapterThanhVien
    private lateinit var listThanhVien: ArrayList<ThanhVien>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentQltvBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setHasOptionsMenu(true)
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
        imgView = binding.dialogQltvImg

        imgView.setOnClickListener {
            getURIImageFromGarelly()
        }
        hoTen.editText!!.requestFocus()
        if(type!=0){
            hoTen.editText!!.setText(thanhVien.hoTen)
            ngaySinh.editText!!.setText(thanhVien.namSinh)
            maTV.editText!!.setText(thanhVien.maTV.toString())
            imgView.setImageURI(Uri.parse(thanhVien.img))
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
                thanhVien.img = uri.toString()
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



    fun getURIImageFromGarelly(){
        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1).start(requireContext(),this@QLTVFragment)
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                Log.d("requestPermisstion","denied")
            }
        }
        TedPermission.create()
            .setPermissionListener(permissionlistener)
            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
            .check()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
           try {
               val result = CropImage.getActivityResult(data)
               imgView.setImageURI(result.uri)
               uri = result.uri
           } catch (e:Exception){
               e.printStackTrace()
           }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main,menu)
        super.onCreateOptionsMenu(menu, inflater)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapterThanhvien.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterThanhvien.filter.filter(newText)
                return false
            }

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}