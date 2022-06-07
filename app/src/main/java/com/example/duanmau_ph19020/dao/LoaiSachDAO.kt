package com.example.duanmau_ph19020.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import com.example.duanmau_ph19020.dao.TempFunc.Companion.getData
import com.example.duanmau_ph19020.database.SQLiteHelper
import com.example.duanmau_ph19020.model.LoaiSach

class LoaiSachDAO(context: Context) {
    //create table LoaiSach(maLoai INTEGER PRIMARY key AUTOINCREMENT, tenLoai text not null)
    private var context:Context
    private lateinit var db:SQLiteDatabase
    private var sqliteHelper:SQLiteHelper
    init {
        this.context = context
        sqliteHelper = SQLiteHelper(context)
    }
    fun insert(loaiSach: LoaiSach){
        db = sqliteHelper.writableDatabase
        val values = ContentValues()
        values.put("tenLoai",loaiSach.tenLoai)
        val result = if(db.insert("LoaiSach",null,values)<0) "Them loai sach that bai" else "them loai sach thanh cong"
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show()
        db.close()
    }

    fun update(loaiSach: LoaiSach){
        db = sqliteHelper.writableDatabase
        val values = ContentValues()
        values.put("tenLoai",loaiSach.tenLoai)
        val result = if(db.update("LoaiSach",values,"maLoai='${loaiSach.maLoai}' ",null)<0) "Them loai sach that bai" else "them loai sach thanh cong"
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show()
        db.close()
    }
    fun remove(loaiSach: LoaiSach){
        db = sqliteHelper.writableDatabase
        val result = if(db.delete("LoaiSach","maloai='${loaiSach.maLoai}' ",null)<=0)"Xoa loai sach that bai" else "Xoa loai sach thanh cong"
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show()
        db.close()
    }

    fun getAll() = getData<LoaiSach>("SELECT *FROM LoaiSach",context)
    fun getID(id:String) = getData<LoaiSach>("SELECT *FROM LoaiSach WHERE maLoai = '$id' ",context)[0]

}