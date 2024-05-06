package com.example.composepractice.driver

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.composepractice.Database


object ConnectionDriver{

    fun getSqlDriver(context: Context)= AndroidSqliteDriver(Database.Schema,context,"Database.db")
}