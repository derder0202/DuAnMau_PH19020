package com.example.duanmau_ph19020.dao

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.EditText
import com.example.duanmau_ph19020.database.SQLiteHelper
import com.example.duanmau_ph19020.fragments.*
import com.example.duanmau_ph19020.model.*
import com.google.android.material.textfield.TextInputLayout
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import dev.shreyaspatil.MaterialDialog.MaterialDialog
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("SimpleDateFormat")
class TempFunc {
    companion object{
        fun checkField(vararg check: TextInputLayout):Boolean{
            var count = 0
            for (view in check){
                if(view.editText!!.text.isEmpty()){
                    view.error = "Bạn phải nhập vào trường này"
                    count++
                } else view.error = null
            }
            return count==0
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
        fun resetField(vararg field:EditText){
            for(i in field){
                i.text = null
            }
            field[0].requestFocus()
        }
        fun <T> listOjectToString(list:ArrayList<T>):ArrayList<String>{
            val listString = ArrayList<String>()
            for(i in list){
                listString.add(i.toString())
            }
            return listString
        }
        fun removeDialog(objectAny: Any,context: Context, fragmentAny:Any){

            val builder = MaterialDialog.Builder(context as Activity)
                .setTitle(
                    when(objectAny){
                        is ThanhVien -> "Xóa thành viên"
                        is LoaiSach -> "Xóa loại sách"
                        is PhieuMuon -> "Xóa phiếu mượn"
                        is Sach -> "Xóa sách"
                        is ThuThu -> "Xóa thủ thư"
                        else -> {null}
                    }.toString()
                )
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
                            (fragmentAny as QLTKFragment).updateRecylerView()
                        }
                    }
                    dialogInterface.dismiss()
                }
                .setNegativeButton("NO") { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
            val dialog = builder.build()
            dialog.show()
        }
        inline fun <reified T> getData(sql:String, context: Context):ArrayList<T>{
            val list = ArrayList<T>()
            val sqLiteDatabase = SQLiteHelper(context).writableDatabase
            val cursor = sqLiteDatabase.rawQuery(sql,null)
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            cursor.moveToFirst()
            while (!cursor.isAfterLast){
                when(T::class.java){
                    ThanhVien::class.java -> list.add(ThanhVien(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)) as T)
                    LoaiSach::class.java -> list.add(LoaiSach(cursor.getInt(0),cursor.getString(1)) as T)
                    PhieuMuon::class.java -> list.add(PhieuMuon(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3),sdf.parse(cursor.getString(4)) as Date,cursor.getInt(5),cursor.getInt(6)) as T)
                    Sach::class.java -> list.add(Sach(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3)) as T)
                    ThuThu::class.java -> list.add(ThuThu(cursor.getString(0),cursor.getString(1),cursor.getString(2)) as T)
                    TopTen::class.java -> list.add(TopTen(cursor.getString(0),cursor.getInt(1)) as T)
                    else -> throw IllegalStateException("Type not supported")
                }
                cursor.moveToNext()
            }
            cursor.close()
            sqLiteDatabase.close()
            return list
        }

    }
}

