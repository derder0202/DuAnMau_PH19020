package com.example.duanmau_ph19020.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import com.example.duanmau_ph19020.database.SQLite_Helper
import com.example.duanmau_ph19020.model.ThuThu

class ThuThuDAO(context: Context) {
    private var db: SQLiteDatabase
    private var sqliteHelper: SQLite_Helper
    private var context:Context
    init {
        this.context = context
        sqliteHelper = SQLite_Helper(context)
        db = sqliteHelper.writableDatabase

    }


    //CREATE TABLE ThuThu(maTT text PRIMARY key not null,hoTen text not null,matKhau text not null)
    fun insert(thuThu: ThuThu){
        val values = ContentValues()
        values.put("maTT",thuThu.maTT)
        values.put("hoTen",thuThu.hoTen)
        values.put("matKhau",thuThu.matKhau)
        val result = if(db.insert("ThuThu",null,values)<0) "them thu thu that bai" else "them thu thu thanh cong"
        Toast.makeText(context,result, Toast.LENGTH_SHORT).show()
    }
    fun update(thuThu: ThuThu){
        val values = ContentValues()
        values.put("hoTen",thuThu.hoTen)
        values.put("matKhau",thuThu.matKhau)
        val result = if(db.update("ThuThu",values,"maTT= '${thuThu.maTT}' ",null)<=0) "doi mat khau that bai" else "doi mat khau thanh cong"
        Toast.makeText(context,result, Toast.LENGTH_SHORT).show()
    }
    fun remove(thuThu: ThuThu){
        val result = if(db.delete("ThuThu","maTT= '${thuThu.maTT}' ",null)<=0) "xoa thu thu that bai" else "xoa thu thu thanh cong"
        Toast.makeText(context,result, Toast.LENGTH_SHORT).show()
    }

    fun getData(sql:String):ArrayList<ThuThu>{
        val list = ArrayList<ThuThu>()
        val cursor:Cursor = db.rawQuery(sql,null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast){
            list.add(ThuThu(cursor.getString(0),cursor.getString(1),cursor.getString(2)))
            cursor.moveToNext()
        }
        cursor.close()
        return list
    }
    fun getAll() = getData("SELECT *FROM ThuThu")
    fun getID(id:String) = getData("SELECT *FROM ThuThu WHERE maTT= '$id' ")[0]
    fun checkLogin(id: String,password:String) = getData("SELECT *FROM ThuThu WHERE maTT like '$id' AND matKhau like '$password' ").size != 0
}