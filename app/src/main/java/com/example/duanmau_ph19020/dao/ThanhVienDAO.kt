package com.example.duanmau_ph19020.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import com.example.duanmau_ph19020.dao.TempFunc.Companion.getData
import com.example.duanmau_ph19020.database.SQLiteHelper
import com.example.duanmau_ph19020.model.ThanhVien

class ThanhVienDAO (context: Context){
    private lateinit var db:SQLiteDatabase
    private var sqliteHelper:SQLiteHelper
    private var context:Context
    init {
        this.context = context
        sqliteHelper = SQLiteHelper(context)
    }
    //"create table ThanhVien(maTV INTEGER primary key AUTOINCREMENT,hoTen text not NULL,namSinh text NOT NULL)\n"
    fun insert(thanhVien: ThanhVien){
        db = sqliteHelper.writableDatabase
        val values = ContentValues()
        values.put("hoTen",thanhVien.hoTen)
        values.put("namSinh",thanhVien.namSinh)
        values.put("img",thanhVien.img)
        val result = if(db.insert("ThanhVien",null,values)<0) "them thanh vien that bai" else "them thanh vien thanh cong"
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show()
        db.close()
    }
    fun update(thanhVien: ThanhVien){
        db = sqliteHelper.writableDatabase
        val values = ContentValues()
        values.put("hoTen",thanhVien.hoTen)
        values.put("namSinh",thanhVien.namSinh)
        values.put("img",thanhVien.img)
        val result = if(db.update("ThanhVien",values,"maTV= ${thanhVien.maTV} ",null)<=0) "cap nhat thanh vien that bai" else "cap nhat thanh vien thanh cong"
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show()
        db.close()
    }
    fun remove(thanhVien: ThanhVien){
        db = sqliteHelper.writableDatabase
        val result = if(db.delete("ThanhVien","maTV= ${thanhVien.maTV} ",null)<=0) "xoa thanh vien that bai" else "xoa thanh vien thanh cong"
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show()
        db.close()
    }

    fun getAll() = getData<ThanhVien>("SELECT *FROM ThanhVien",context)
    fun getID(id:String) = getData<ThanhVien>("SELECT *FROM ThanhVien WHERE maTV= '$id' ",context)[0]


}