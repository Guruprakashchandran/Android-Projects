package com.example.myapplication.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object StringValidationUtil {

    fun checkEmployeeName(name: () -> String?) =  !name.invoke().isNullOrEmpty()

    fun checkEmployeeMobileNo(no: () -> Long) = no.invoke() in 6000000000..9999999999

    fun checkEmployeeMailId(id: () -> String): Boolean {
        val mailId: String = id.invoke()
        val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$")
        return mailId.isNotEmpty() && emailRegex.matches(mailId)
    }

    fun checkEmployeeAddress(address: () -> String?) = !address.invoke().isNullOrEmpty()

    fun checkEmployeeExperience(experience: () -> Long): Boolean {
        val ex = experience.invoke()
        return ex > -1 && ex < 40
    }

    fun checkEmployeeSalary(salary: () -> Long) = salary.invoke() > 0
    fun checkEmpId(empId: () -> String) : Boolean {

        with(empId.invoke()){
            return this.isNotEmpty()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkDob(dob: () -> String?): Boolean {

        val dateOfBirth = dob.invoke() ?: return false
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val current = LocalDateTime.now().format(formatter)
        val dobArray = dateOfBirth.split("-")
        val currArray = current.split("-")
        val valueOfDob = dobArray[0].toInt() + dobArray[1].toInt() + dobArray[2].toInt()
        val valueOfCurrentDate = currArray[0].toInt() + currArray[1].toInt() + currArray[2].toInt()
        return valueOfDob < valueOfCurrentDate
    }

}