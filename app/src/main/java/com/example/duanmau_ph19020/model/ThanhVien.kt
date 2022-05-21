package com.example.duanmau_ph19020.model

class ThanhVien(var maTV:Int=0,var hoTen:String="",var namSinh:String=""){
    override fun toString(): String {
        return "$maTV\t\t\t$hoTen"
    }
}