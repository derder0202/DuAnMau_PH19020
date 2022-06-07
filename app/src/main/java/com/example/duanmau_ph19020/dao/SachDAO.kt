package com.example.duanmau_ph19020.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import com.example.duanmau_ph19020.dao.TempFunc.Companion.getData
import com.example.duanmau_ph19020.database.SQLiteHelper
import com.example.duanmau_ph19020.model.LoaiSach
import com.example.duanmau_ph19020.model.Sach

class SachDAO(context: Context) {
    private var context:Context
    private lateinit var db:SQLiteDatabase
    private var sqliteHelper:SQLiteHelper
    init {
        this.context = context
        sqliteHelper = SQLiteHelper(context)
    }
    //create table Sach(maSach INTEGER PRIMARY KEY AUTOINCREMENT,tenSach text not null,giaThue INTEGER not null,maloai INTEGER REFERENCES LoaiSach(maloai))
    fun insert(sach:Sach){
        db = sqliteHelper.writableDatabase
        val values = ContentValues()
        values.put("tenSach",sach.tenSach)
        values.put("giaThue",sach.giaThue)
        values.put("maLoai",sach.maLoai)
        val result = if(db.insert("Sach",null,values)<0)"Them sach that bai" else "Them sach thanh cong"
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show()
        db.close()
    }
    fun update(sach: Sach){
        db = sqliteHelper.writableDatabase
        val values = ContentValues()
        values.put("tenSach",sach.tenSach)
        values.put("giaThue",sach.giaThue)
        values.put("maLoai",sach.maLoai)
        val result = if(db.update("Sach",values,"maSach= '${sach.maSach}' ",null)<=0)"Cap nhat sach that bai" else "Cap nhat sach thanh cong"
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show()
        db.close()
    }

    fun remove(sach: Sach){
        db = sqliteHelper.writableDatabase
        val result = if(db.delete("Sach","maSach= '${sach.maSach}' ",null)<=0) "Xoa sach that bai" else "Xoa sach thanh cong"
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show()
        db.close()
    }

    fun getAll() = getData<Sach>("SELECT *FROM Sach",context)
    fun getID(id:String) = getData<Sach>("SELECT *FROM Sach WHERE maSach = '$id' ",context)[0]

}