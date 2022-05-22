package com.example.duanmau_ph19020.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
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
        val result = if(db.insert("ThanhVien",null,values)<0) "them thanh vien that bai" else "them thanh vien thanh cong"
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show()
        db.close()
    }
    fun update(thanhVien: ThanhVien){
        db = sqliteHelper.writableDatabase
        val values = ContentValues()
        values.put("hoTen",thanhVien.hoTen)
        values.put("namSinh",thanhVien.namSinh)
        val result = if(db.update("ThanhVien",values,"maTV= ${thanhVien.maTV} ",null)<=0) "cap nhat thanh vien that bai" else "cap nhat thanh vien thanh cong"
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show()
        db.close()
    }
    fun remove(thanhVien: ThanhVien){
        db = sqliteHelper.writableDatabase
        val result = if(db.delete("ThanhVien","maTV= ${thanhVien.maTV} ",null)<=0) "xoa thanh vien that bai" else "xoa thanh vien thanh cong"
        PhieuMuonDAO(context).removebyTV(thanhVien)
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show()
        db.close()
    }

    fun getData(sql:String):ArrayList<ThanhVien>{
        db = sqliteHelper.writableDatabase
        val list = ArrayList<ThanhVien>()
        val cursor = db.rawQuery(sql,null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast){
            list.add(ThanhVien(cursor.getInt(0),cursor.getString(1),cursor.getString(2)))
            cursor.moveToNext()
        }
        cursor.close()
        db.close()
        return list
    }
    fun getAll() = getData("SELECT *FROM ThanhVien")
    fun getID(id:String) = getData("SELECT *FROM ThanhVien WHERE maTV= '$id' ")[0]


}