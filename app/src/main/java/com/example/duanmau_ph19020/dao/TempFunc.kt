package com.example.duanmau_ph19020.dao
import android.app.AlertDialog
import android.content.Context
import com.example.duanmau_ph19020.fragments.QLLSFragment
import com.example.duanmau_ph19020.fragments.QLPMFragment
import com.example.duanmau_ph19020.fragments.QLSFragment
import com.example.duanmau_ph19020.fragments.QLTVFragment
import com.example.duanmau_ph19020.model.*
import com.google.android.material.textfield.TextInputLayout

class TempFunc {
    companion object{
        fun checkField(check: TextInputLayout):Boolean{
            return if(check.editText!!.text.isEmpty()){
                check.error = "Bạn phải nhập vào trường này"
                false
            } else{
                check.error = null
                true
            }
        }
        fun checkNumber(check: TextInputLayout):Boolean{
            try {
                if (check.editText!!.text.toString().toInt()<=0){
                    check.error = "Giá phải là số lớn hơn 0"
                    return false
                }
            } catch (e:Exception){
                check.error = "Giá phải là số lớn hơn 0"
                e.printStackTrace()
                return false
            }
            return true
        }
        fun <T> listOjectToString(list:ArrayList<T>):ArrayList<String>{
            val listString = ArrayList<String>()
            for(i in list){
                listString.add(i.toString())
            }
            return listString
        }
        fun removeDialog(objectAny: Any,context: Context,fragmentAny:Any){
            val builder = AlertDialog.Builder(context)
                .setTitle(when(objectAny){
                    is ThanhVien -> "Xóa thành viên"
                    is LoaiSach -> "Xóa loại sách"
                    is PhieuMuon -> "Xóa phiếu mượn"
                    is Sach -> "Xóa sách"
                    is ThuThu -> "Xóa thủ thư"
                    else -> {null}
                })
                .setMessage("Bạn có muốn xóa không")
                .setCancelable(true)
                .setPositiveButton("YES") { dialogInterface, i ->
                    when(objectAny){
                        is ThanhVien -> {
                            ThanhVienDAO(context).remove(objectAny)
                            (fragmentAny as QLTVFragment).updateRecylerView()
                        }
                        is LoaiSach -> {
                            LoaiSachDAO(context).remove(objectAny)
                            (fragmentAny as QLLSFragment).updateRecylerView()
                        }
                        is PhieuMuon -> {
                            PhieuMuonDAO(context).remove(objectAny)
                            (fragmentAny as QLPMFragment).updateRecylerView()
                        }
                        is Sach ->  {
                            SachDAO(context).remove(objectAny)
                            (fragmentAny as QLSFragment).updateRecylerView()
                        }
                        is ThuThu -> ThuThuDAO(context).remove(objectAny)
                    }
                    dialogInterface.dismiss()
                }
                .setNegativeButton("NO") { dialogInterface, i ->
                    dialogInterface.dismiss()
                }
            val dialog = builder.create()
            dialog.show()
        }

    }
}

