package com.example.todoapp.data.application

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.todoapp.Database
import com.example.todoapp.data.database.CategoryBasedToDoListImpl
import com.example.todoapp.data.database.ToDoCategoryImpl
import com.example.todoapp.data.database.ToDoListImpl
import com.example.todoapp.data.repository.ToDoListRepository
import com.example.todoapp.viewmodel.CategoryBasedTodoViewModel
import com.example.todoapp.viewmodel.ToDoListViewModel
import com.example.todoapp.viewmodel.TodoCategoryViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class References {

    @Provides
    @Singleton
    fun getRepositoryInstance(toDoListImpl: ToDoListImpl,toDoCategoryImpl: ToDoCategoryImpl,categoryBasedToDoListImpl: CategoryBasedToDoListImpl) = ToDoListRepository(toDoListImpl,toDoCategoryImpl,categoryBasedToDoListImpl)

    @Provides
    @Singleton
    fun getToDoListInterfaceInstance(database: Database) = ToDoListImpl(database)

    @Provides
    @Singleton
    fun getToDoCategoryInterfaceInstance(database: Database) = ToDoCategoryImpl(database)

    @Provides
    @Singleton
    fun getCategoryBasedToDoListImpl() = CategoryBasedToDoListImpl()

    @Provides
    @Singleton
    fun getDatabaseInstance(@ApplicationContext context: Context) = Database(AndroidSqliteDriver(Database.Schema,context,"Database.db"))
}