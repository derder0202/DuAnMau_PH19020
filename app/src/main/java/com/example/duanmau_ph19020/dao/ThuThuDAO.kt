package com.example.duanmau_ph19020.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import com.example.duanmau_ph19020.dao.TempFunc.Companion.getData
import com.example.duanmau_ph19020.database.SQLiteHelper
import com.example.duanmau_ph19020.model.ThuThu

class ThuThuDAO(context: Context) {
    private lateinit var db: SQLiteDatabase
    private var sqliteHelper: SQLiteHelper
    private var context:Context
    init {
        this.context = context
        sqliteHelper = SQLiteHelper(context)
    }


    //CREATE TABLE ThuThu(maTT text PRIMARY key not null,hoTen text not null,matKhau text not null)
    fun insert(thuThu: ThuThu){
        db = sqliteHelper.writableDatabase
        val values = ContentValues()
        values.put("maTT",thuThu.maTT)
        values.put("hoTen",thuThu.hoTen)
        values.put("matKhau",thuThu.matKhau)
        val result = if(db.insert("ThuThu",null,values)<0) "them thu thu that bai" else "them thu thu thanh cong"
        Toast.makeText(context,result, Toast.LENGTH_SHORT).show()
        db.close()
    }
    fun update(thuThu: ThuThu){
        db = sqliteHelper.writableDatabase
        val values = ContentValues()
        values.put("hoTen",thuThu.hoTen)
        values.put("matKhau",thuThu.matKhau)
        val result = if(db.update("ThuThu",values,"maTT= '${thuThu.maTT}' ",null)<=0) "doi mat khau that bai" else "doi mat khau thanh cong"
        Toast.makeText(context,result, Toast.LENGTH_SHORT).show()
        db.close()
    }
    fun remove(thuThu: ThuThu){
        db = sqliteHelper.writableDatabase
        val result = if(db.delete("ThuThu","maTT= '${thuThu.maTT}' ",null)<=0) "xoa thu thu that bai" else "xoa thu thu thanh cong"
        Toast.makeText(context,result, Toast.LENGTH_SHORT).show()
        db.close()
    }

    fun getAll() = getData<ThuThu>("SELECT *FROM ThuThu",context)
    fun getID(id:String) = getData<ThuThu>("SELECT *FROM ThuThu WHERE maTT= '$id' ",context)[0]
    fun checkLogin(id: String,password:String) = getData<ThuThu>("SELECT *FROM ThuThu WHERE maTT like '$id' AND matKhau like '$password' ",context).size != 0
}