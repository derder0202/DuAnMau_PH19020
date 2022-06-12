package com.example.duanmau_ph19020.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, name, null, version){
    companion object{
        const val name = "QLTV"
        const val version = 1
    }

    val defaultData = """ INSERT INTO ThuThu(maTT,hoTen,matKhau) values ('admin','toi la admin','admin') """

    override fun onCreate(p0: SQLiteDatabase) {
        p0.execSQL("create table ThanhVien(maTV INTEGER primary key AUTOINCREMENT,hoTen text not NULL,namSinh text NOT NULL,img text)\n")
        p0.execSQL("CREATE TABLE ThuThu(maTT text PRIMARY key not null,hoTen text not null,matKhau text not null)\n")
        p0.execSQL("create table LoaiSach(maLoai INTEGER PRIMARY key AUTOINCREMENT, tenLoai text not null)\n")
        p0.execSQL("create table Sach(maSach INTEGER PRIMARY KEY AUTOINCREMENT,tenSach text not null,giaThue INTEGER not null,maloai INTEGER REFERENCES LoaiSach(maloai))\n")
        p0.execSQL("CREATE TABLE PhieuMuon(maPM INTEGER PRIMARY KEY AUTOINCREMENT,maTT text not null REFERENCES ThuThu(maTT),maTV INTEGER not null REFERENCES ThanhVien(maTV),maSach INTEGER not null REFERENCES Sach(maSach),Ngay date not null,traSach INTEGER not null,tienThue integer not null)\n")
        p0.execSQL(defaultData)
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {
    }
}