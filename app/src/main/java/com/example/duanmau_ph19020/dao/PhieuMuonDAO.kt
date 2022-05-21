package com.example.duanmau_ph19020.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import com.example.duanmau_ph19020.database.SQLiteHelper
import com.example.duanmau_ph19020.model.PhieuMuon
import com.example.duanmau_ph19020.model.TopTen
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PhieuMuonDAO(context: Context) {
    var context:Context
    var sqliteHelper:SQLiteHelper
    var db:SQLiteDatabase
    @SuppressLint("SimpleDateFormat")
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    init {
        this.context = context
        sqliteHelper = SQLiteHelper(context)
        db = sqliteHelper.writableDatabase
    }

    //CREATE TABLE PhieuMuon(maPM INTEGER PRIMARY KEY AUTOINCREMENT,maTT text not null REFERENCES ThuThu(maTT),maTV INTEGER not null REFERENCES ThanhVien(maTV),
// maSach INTEGER not null REFERENCES Sach(maSach),Ngay date not null,traSach INTEGER not null,tienThue integer not null)
    fun insert(phieuMuon: PhieuMuon){
        val values = ContentValues()
        values.put("maTT",phieuMuon.maTT)
        values.put("maTV",phieuMuon.maTV)
        values.put("maSach",phieuMuon.maSach)
        values.put("Ngay",sdf.format(phieuMuon.ngay))
        values.put("traSach",phieuMuon.traSach)
        values.put("tienThue",phieuMuon.tienThue)
        val result = if(db.insert("PhieuMuon",null,values)<0) "Them phieu muon that bai" else "Them phieu muon thanh cong"
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show()
    }

    fun update(phieuMuon: PhieuMuon){
        val values = ContentValues()
        values.put("maTT",phieuMuon.maTT)
        values.put("maTV",phieuMuon.maTV)
        values.put("maSach",phieuMuon.maSach)
        values.put("Ngay",sdf.format(phieuMuon.ngay))
        values.put("traSach",phieuMuon.traSach)
        values.put("tienThue",phieuMuon.tienThue)
        val result = if(db.update("PhieuMuon",values,"maPM= '${phieuMuon.maPM}' ",null)<=0) "Sua thong tin phieu muon that bai" else "Sua thong tin phieu muon thanh cong"
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show()
    }

    fun remove(phieuMuon: PhieuMuon){
        val result = if(db.delete("PhieuMuon","maPM= '${phieuMuon.maPM}' ",null)<=0) "Xoa phieu muon that bai" else "Xoa phieu muon thanh cong"
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show()
    }

    fun getData(sql:String):ArrayList<PhieuMuon>{
        val list = ArrayList<PhieuMuon>()
        val cursor = db.rawQuery(sql,null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast){
            list.add(PhieuMuon(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3),sdf.parse(cursor.getString(4)) as Date,cursor.getInt(5),cursor.getInt(6)))
            cursor.moveToNext()
        }
        cursor.close()
        return list
    }
    fun getAll() = getData("SELECT *FROM PhieuMuon")
    fun getID(id:String) = getData("SELECT *FROM PhieuMuon WHERE maPM = '$id' ")[0]

    fun getTop():ArrayList<TopTen>{
        val list = ArrayList<TopTen>()
        val cursor = db.rawQuery("SELECT Sach.tenSach, count(PhieuMuon.maSach) FROM PhieuMuon \n" +
                "JOIN Sach on Sach.maSach = PhieuMuon.MaSach\n" +
                "GROUP BY Sach.tenSach ORDER BY count(PhieuMuon.maSach) DESC LIMIT 10  ",null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast){
            list.add(TopTen(cursor.getString(0),cursor.getInt(1)))
            cursor.moveToNext()
        }
        cursor.close()
        return list
    }

    fun getDoanhThu(tuNgay:String,denNgay:String):Int{
        val list = ArrayList<Int>()
        val cursor = db.rawQuery("SELECT SUM(tienThue) FROM PhieuMuon WHERE Ngay BETWEEN '$tuNgay' AND '$denNgay' ",null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast){
            list.add(cursor.getInt(0))
            cursor.moveToNext()
        }
        cursor.close()
        return list[0]
    }
}