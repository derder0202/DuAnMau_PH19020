package com.example.duanmau_ph19020.dao
import android.annotation.SuppressLint
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

@SuppressLint("SimpleDateFormat")
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
        fun removeDialog(objectAny: Any,context: Context, fragmentAny:Any){
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
            val sqLiteDatabase = SQLiteHelper(context).writableDatabase
            val cursor = sqLiteDatabase.rawQuery(sql,null)
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            cursor.moveToFirst()
            while (!cursor.isAfterLast){
                when(T::class.java){
                    ThanhVien::class.java -> list.add(ThanhVien(cursor.getInt(0),cursor.getString(1),cursor.getString(2)) as T)
                    LoaiSach::class.java -> list.add(LoaiSach(cursor.getInt(0),cursor.getString(1)) as T)
                    PhieuMuon::class.java -> list.add(PhieuMuon(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3),sdf.parse(cursor.getString(4)) as Date,cursor.getInt(5),cursor.getInt(6)) as T)
                    Sach::class.java -> list.add(Sach(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3)) as T)
                    ThuThu::class.java -> list.add(ThuThu(cursor.getString(0),cursor.getString(1),cursor.getString(2)) as T)
                    TopTen::class.java -> list.add(TopTen(cursor.getString(0),cursor.getInt(1)) as T)
                }
                cursor.moveToNext()
            }
            cursor.close()
            return list
        }
    }
}

