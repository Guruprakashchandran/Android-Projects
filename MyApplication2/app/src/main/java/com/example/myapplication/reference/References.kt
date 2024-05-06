package com.example.myapplication.reference

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.myapplication.driver.ConnectionDriver
import com.example.myapplication.databaseConnection.EmpDetailsManipulateImpl
import com.example.myapplication.repository.EmployeeRepo
import com.example.myapplication2.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object References {

    @Provides
    @Singleton
    fun getRepositoryReference(empDetailsManipulateImpl: EmpDetailsManipulateImpl) = EmployeeRepo(empDetailsManipulateImpl)

    @Provides
    @Singleton
    fun getReferenceForDetailsFromTable(database: Database) = EmpDetailsManipulateImpl(database)

    @Provides
    @Singleton
    fun getDriverReference() = ConnectionDriver

    @Provides
    @Singleton
    fun getDatabaseReference(@ApplicationContext context: Context) = Database(AndroidSqliteDriver(Database.Schema, context,"Database.db"))
}