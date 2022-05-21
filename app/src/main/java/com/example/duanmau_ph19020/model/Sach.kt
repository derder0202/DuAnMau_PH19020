package com.example.duanmau_ph19020.model

data class Sach(var maSach:Int=0,var tenSach:String="",var giaThue:Int=0,var maLoai:Int=0){
    override fun toString(): String {
        return "$maSach\t\t\t$tenSach"
    }
}