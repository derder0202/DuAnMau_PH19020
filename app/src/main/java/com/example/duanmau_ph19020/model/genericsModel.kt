package com.example.duanmau_ph19020.model

class genericsModel<T>(tclass:Class<T>) {
    private var toObject:T
    init{
        this.toObject = tclass.newInstance() as T
    }
    fun getObject():T{
        return this.toObject
    }
}