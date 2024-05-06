package com.example.todoapp.data.repository

import com.example.todoapp.data.database.CategoryBasedToDoListImpl
import com.example.todoapp.data.database.ToDoCategoryImpl
import com.example.todoapp.data.database.ToDoListImpl
import com.example.todoapp.data.dataclasses.Categories
import com.example.todoapp.data.dataclasses.UserToDoList
import javax.inject.Inject

class ToDoListRepository @Inject constructor(
    private var toDoListImpl: ToDoListImpl,
    private var toDoCategoryImpl: ToDoCategoryImpl,
    private var categoryBasedToDoListImpl: CategoryBasedToDoListImpl
) {

    suspend fun getAllToDoCategories(): List<Categories> {
        return toDoCategoryImpl.getAllCategories().sortedBy { it.rowId }
    }

    suspend fun getAllToDoLists(): List<UserToDoList> {

        return toDoListImpl.getAllTodos().sortedByDescending { it.no }
    }

    suspend fun addToDoList(toDoList: String): Any {

        val toDo = UserToDoList(
            no = 0L,
            text = toDoList,
            category = "Personal",
            status = "InCompleted",
            createdTime = "",
            modifiedTime = "",
            createdId = 0L,
            rowId = 0L
        )
        //        if (isFinished) {
//            listOfTodos = getAllToDoLists().sortedBy { it.no }
//        }
//        listOfTodos.add(toDo)
        return toDoListImpl.insertToDo(
            toDo
        )
//        return isFinished
    }

    suspend fun addToDoCategory(category: Categories): Boolean {

        return toDoCategoryImpl.addCategory(category)
//        return getAllToDoCategories().sortedBy { it.rowId }
    }

    suspend fun updateToDoListByStatus(toDoList: UserToDoList): Boolean {

        return if (toDoList.status == "InCompleted") {
            toDoListImpl.updateToDo(
                UserToDoList(
                    no = toDoList.no,
                    text = toDoList.text,
                    category = toDoList.category,
                    status = "Completed",
                    createdTime = toDoList.createdTime,
                    modifiedTime = toDoList.modifiedTime,
                    createdId = toDoList.createdId,
                    rowId = toDoList.rowId
                )
            )
        } else {
            toDoListImpl.updateToDo(
                UserToDoList(
                    no = toDoList.no,
                    text = toDoList.text,
                    category = toDoList.category,
                    status = "InCompleted",
                    createdTime = toDoList.createdTime,
                    modifiedTime = toDoList.modifiedTime,
                    createdId = toDoList.createdId,
                    rowId = toDoList.rowId
                )
            )
        }
    }

    suspend fun updateCategoryBasedToDoListByStatus(
        toDoList: UserToDoList,
        category: String
    ): Boolean {
        return if (toDoList.status == "InCompleted") {
            toDoListImpl.updateToDo(
                UserToDoList(
                    no = toDoList.no,
                    text = toDoList.text,
                    category = toDoList.category,
                    status = "Completed",
                    createdTime = toDoList.createdTime,
                    modifiedTime = toDoList.modifiedTime,
                    createdId = toDoList.createdId,
                    rowId = toDoList.rowId
                )
            )
        } else {
            toDoListImpl.updateToDo(
                UserToDoList(
                    no = toDoList.no,
                    text = toDoList.text,
                    category = toDoList.category,
                    status = "InCompleted",
                    createdTime = toDoList.createdTime,
                    modifiedTime = toDoList.modifiedTime,
                    createdId = toDoList.createdId,
                    rowId = toDoList.rowId
                )
            )
        }
    }

    suspend fun deleteToDoList(rowId: Long): Any {

        return toDoListImpl.deleteDoTo(rowId)
    }

    suspend fun deleteFilteredToDoList(rowId: Long, category: String): Any {

        return toDoListImpl.deleteDoTo(rowId)
    }

    suspend fun addNewToDoData(toDoList: String, category: String): Any {

        val toDoData = UserToDoList(
            0,
            toDoList,
            category = category,
            status = "InCompleted",
            createdTime = "",
            modifiedTime = "",
            createdId = 0L,
            rowId = 0L
        )
        return toDoListImpl.insertToDo(
            toDoData
        )
//        if (isFinished) {
//            listOfTodos = getFilteredToDo(category = category).sortedBy { it.no }
//        }
    }

    suspend fun getFilteredToDo(category: String): List<UserToDoList> {
        return categoryBasedToDoListImpl.getFilteredToDoList(category).sortedByDescending { it.no }
    }
}