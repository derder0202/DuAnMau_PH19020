package com.example.duanmau_ph19020.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import com.example.duanmau_ph19020.database.SQLiteHelper
import com.example.duanmau_ph19020.model.Sach

class SachDAO(context: Context) {
    private var context:Context
    private var db:SQLiteDatabase
    private var sqliteHelper:SQLiteHelper
    init {
        this.context = context
        sqliteHelper = SQLiteHelper(context)
        db = sqliteHelper.writableDatabase
    }
    //create table Sach(maSach INTEGER PRIMARY KEY AUTOINCREMENT,tenSach text not null,giaThue INTEGER not null,maloai INTEGER REFERENCES LoaiSach(maloai))
    fun insert(sach:Sach){
        val values = ContentValues()
        values.put("tenSach",sach.tenSach)
        values.put("giaThue",sach.giaThue)
        values.put("maLoai",sach.maLoai)
        val result = if(db.insert("Sach",null,values)<0)"Them sach that bai" else "Them sach thanh cong"
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show()
    }
    fun update(sach: Sach){
        val values = ContentValues()
        values.put("tenSach",sach.tenSach)
        values.put("giaThue",sach.giaThue)
        values.put("maLoai",sach.maLoai)
        val result = if(db.update("Sach",values,"maSach= '${sach.maSach}' ",null)<=0)"Cap nhat sach that bai" else "Cap nhat sach thanh cong"
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show()
    }

    fun remove(sach: Sach){
        val result = if(db.delete("Sach","maSach= '${sach.maSach}' ",null)<=0) "Xoa sach that bai" else "Xoa sach thanh cong"
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show()
    }

    fun getData(sql:String):ArrayList<Sach>{
        val list = ArrayList<Sach>()
        val cursor = db.rawQuery(sql,null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast){
            list.add(Sach(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3)))
            cursor.moveToNext()
        }
        cursor.close()
        return list
    }

    fun getAll() = getData("SELECT *FROM Sach")
    fun getID(id:String) = getData("SELECT *FROM Sach WHERE maSach = '$id' ")[0]

}