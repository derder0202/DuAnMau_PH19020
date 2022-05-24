package com.example.duanmau_ph19020.dao
import android.app.AlertDialog
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.duanmau_ph19020.database.SQLiteHelper
import com.example.duanmau_ph19020.fragments.*
import com.example.duanmau_ph19020.model.*
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.typeOf

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
                .setPositiveButton("YES") { dialogInterface, _ ->
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
                        is ThuThu -> {
                            ThuThuDAO(context).remove(objectAny)
                            (fragmentAny as TaoTaiKhoanFragment).updateRecylerView()
                        }
                    }
                    dialogInterface.dismiss()
                }
                .setNegativeButton("NO") { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
            val dialog = builder.create()
            dialog.show()
        }
        inline fun <reified T> getData(sql:String, context: Context):ArrayList<T>{
            val list = ArrayList<T>()
            val sqLiteHelper = SQLiteHelper(context)
            val sqLiteDatabase = sqLiteHelper.writableDatabase
            val cursor = sqLiteDatabase.rawQuery(sql,null)
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            cursor.moveToFirst()
            while (!cursor.isAfterLast){
                val model = genericsModel(T::class.java).getObject()
                when(model){
                    is ThanhVien -> {
                        model.maTV = cursor.getInt(0)
                        model.hoTen = cursor.getString(1)
                        model.namSinh = cursor.getString(2)
                    }
                    is LoaiSach -> {
                        model.maLoai = cursor.getInt(0)
                        model.tenLoai = cursor.getString(1)
                    }
                    // PhieuMuon(var maPM:Int=0,var maTT:String="",var maTV:Int=0,var maSach:Int=0,var ngay:Date=Date(),var traSach:Int=0,var tienThue:Int=0)
                    is PhieuMuon -> {
                        model.maPM = cursor.getInt(0)
                        model.maTT = cursor.getString(1)
                        model.maTV = cursor.getInt(2)
                        model.maSach = cursor.getInt(3)
                        model.ngay = sdf.parse(cursor.getString(4)) as Date
                        model.traSach = cursor.getInt(5)
                        model.tienThue = cursor.getInt(6)
                    }
                    // Sach(var maSach:Int=0,var tenSach:String="",var giaThue:Int=0,var maLoai:Int=0)
                    is Sach -> {
                        model.maSach = cursor.getInt(0)
                        model.tenSach = cursor.getString(1)
                        model.giaThue = cursor.getInt(2)
                        model.maLoai = cursor.getInt(3)
                    }
                    is ThuThu -> {
                        model.maTT = cursor.getString(0)
                        model.hoTen = cursor.getString(1)
                        model.matKhau = cursor.getString(2)
                    }
                }
                list.add(model)
                cursor.moveToNext()
            }
            cursor.close()
            return list
        }
    }
}

