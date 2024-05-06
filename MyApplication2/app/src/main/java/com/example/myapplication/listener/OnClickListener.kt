package com.example.myapplication.listener

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication2.EmployeeEntity

interface OnClickListener {
    fun onClick(
        employeeEntity: EmployeeEntity,
        transitionName: String,
        squareProfileImage: ImageView,
        transitionTextName: String,
        empDetailName: TextView
    ){
        Log.e("Data","OnClick")
    }
    fun onDelete(employeeEntity: EmployeeEntity){
        Log.e("Data","OnDelete")
    }
    fun selectSalary(value: Long){
        Log.e("Data","0")
    }
}