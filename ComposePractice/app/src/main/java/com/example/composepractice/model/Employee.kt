package com.example.composepractice.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Employee(
    var id: Long,
    var name: String,
    var address: String,
    var emailId: String,
    var mobileNo: Long,
    var yearOfExperience: Long,
    var salary: Long,
    var department: String,
    var designation: String,
    var employeeId: String,
    var dateOfBirth: String,
    var gender: String
) : Parcelable