package com.fenghongzhang.youbo_2307.datasource.main

import javax.inject.Inject

class MainLocalDataSource @Inject constructor(){
    fun getData(){
        println("MainLocalDataSource")
    }
}