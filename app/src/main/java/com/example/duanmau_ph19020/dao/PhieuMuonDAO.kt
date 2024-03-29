package com.example.duanmau_ph19020.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import com.example.duanmau_ph19020.dao.TempFunc.Companion.getData
import com.example.duanmau_ph19020.database.SQLiteHelper
import com.example.duanmau_ph19020.model.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PhieuMuonDAO(context: Context) {
    private var context:Context
    private var sqliteHelper:SQLiteHelper
    private lateinit var db:SQLiteDatabase
    @SuppressLint("SimpleDateFormat")
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    init {
        this.context = context
        sqliteHelper = SQLiteHelper(context)
    }

 //        p0.execSQL("CREATE TABLE PhieuMuon(maPM INTEGER PRIMARY KEY AUTOINCREMENT,maTT text not null REFERENCES ThuThu(maTT),maTV INTEGER not null REFERENCES ThanhVien(maTV),tenTV text not null,
 //        maSach INTEGER not null REFERENCES Sach(maSach),tenSach text not null,Ngay date not null,traSach INTEGER not null,tienThue integer not null)\n")
    fun insert(phieuMuon: PhieuMuon){
        db = sqliteHelper.writableDatabase
        val values = ContentValues()
        values.put("maTT",phieuMuon.maTT)
        values.put("maTV",phieuMuon.maTV)
        values.put("maSach",phieuMuon.maSach)
        values.put("Ngay",sdf.format(phieuMuon.ngay))
        values.put("traSach",phieuMuon.traSach)
        values.put("tienThue",phieuMuon.tienThue)
        val result = if(db.insert("PhieuMuon",null,values)<0) "Them phieu muon that bai" else "Them phieu muon thanh cong"
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show()
        db.close()
    }

    fun update(phieuMuon: PhieuMuon){
        db = sqliteHelper.writableDatabase
        val values = ContentValues()
        values.put("maTT",phieuMuon.maTT)
        values.put("maTV",phieuMuon.maTV)
        values.put("maSach",phieuMuon.maSach)
        values.put("Ngay",sdf.format(phieuMuon.ngay))
        values.put("traSach",phieuMuon.traSach)
        values.put("tienThue",phieuMuon.tienThue)
        val result = if(db.update("PhieuMuon",values,"maPM= '${phieuMuon.maPM}' ",null)<=0) "Sua thong tin phieu muon that bai" else "Sua thong tin phieu muon thanh cong"
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show()
        db.close()
    }

    fun remove(phieuMuon: PhieuMuon){
        db = sqliteHelper.writableDatabase
        val result = if(db.delete("PhieuMuon","maPM= '${phieuMuon.maPM}' ",null)<=0) "Xoa phieu muon that bai" else "Xoa phieu muon thanh cong"
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show()
        db.close()
    }

    fun getAll() = getData<PhieuMuon>("SELECT *FROM PhieuMuon",context)
    fun getID(id:String) = getData<PhieuMuon>("SELECT *FROM PhieuMuon WHERE maPM = '$id' ",context)[0]
    fun getTop() = getData<TopTen>("SELECT Sach.tenSach, count(PhieuMuon.maSach) FROM PhieuMuon \n" +
            "JOIN Sach on Sach.maSach = PhieuMuon.MaSach\n" +
            "GROUP BY Sach.tenSach ORDER BY count(PhieuMuon.maSach) DESC LIMIT 10  ",context)

    fun getDoanhThu(tuNgay:String,denNgay:String):Int{
        db = sqliteHelper.writableDatabase
        val list = ArrayList<Int>()
        val cursor = db.rawQuery("SELECT SUM(tienThue) FROM PhieuMuon WHERE Ngay BETWEEN '$tuNgay' AND '$denNgay' ",null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast){
            list.add(cursor.getInt(0))
            cursor.moveToNext()
        }
        cursor.close()
        db.close()
        return list[0]
    }
}