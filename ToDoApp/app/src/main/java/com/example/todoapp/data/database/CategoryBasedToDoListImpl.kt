package com.example.todoapp.data.database

import com.example.todoapp.data.dataclasses.UserToDoList
import com.zoho.catalyst.common.ZCatalystUtil
import com.zoho.catalyst.datastore.ZCatalystSelectQuery
import com.zoho.catalyst.setup.ZCatalystApp
import kotlinx.coroutines.CompletableDeferred

class CategoryBasedToDoListImpl : CategoryBasedToDoListInterface {
    override suspend fun getFilteredToDoList(category: String): List<UserToDoList> {

        var listOfToDoList: List<UserToDoList>
        val toDoListDeferred = CompletableDeferred<List<UserToDoList>>()
        val query = ZCatalystSelectQuery.Builder().selectAll().from(tableName = "ToDo")
            .where(column = "category", comparator = ZCatalystUtil.Comparator.EQUAL_TO, value = category).build()
        ZCatalystApp.getInstance().getDataStoreInstance().execute(query,
            success = {

                listOfToDoList = parseTodoData(it)
                toDoListDeferred.complete(listOfToDoList)
            },
            failure = {
                toDoListDeferred.complete(emptyList())
                toDoListDeferred.completeExceptionally(it)
            })
        return toDoListDeferred.await()
    }

    private fun parseTodoData(data: List<Map<String, Map<String, Any?>>>): List<UserToDoList> {

        val output: ArrayList<UserToDoList> = ArrayList()
        for (i in data.indices) {
            data[i].values.forEach {
                output.add(
                    UserToDoList(
                        it["no"]?.toString()?.toLong() ?: 0L,
                        it["text"]?.toString() ?: "",
                        it["category"].toString(),
                        it["status"]?.toString() ?: "",
                        it["MODIFIEDTIME"]?.toString() ?: "",
                        it["MODIFIEDTIME"].toString(),
                        it["CREATORID"].toString().toLong(),
                        it["ROWID"].toString().toLong()
                    )
                )
            }
        }
        return output
    }

    override suspend fun addFilteredToDoList(userToDoList: UserToDoList): Any {

        val isAddedDeferred = CompletableDeferred<Any>()
        val newData =
            ZCatalystApp.getInstance().getDataStoreInstance().getTableInstance("ToDo").newRow()
        val no = System.currentTimeMillis()
        newData.setColumnValue("no", no)
        newData.setColumnValue("text", userToDoList.text)
        newData.setColumnValue("category", userToDoList.category)
        newData.setColumnValue("status", userToDoList.status)
        newData.create(
            success = {
                userToDoList.apply {
                    val toDo = UserToDoList(
                        no = no,
                        text = text,
                        category = category,
                        status = status,
                        rowId = it.id,
                        createdId = it.creatorId,
                        createdTime = it.createdTime,
                        modifiedTime = it.modifiedTime
                    )
                    isAddedDeferred.complete(toDo)
//                val query = ZCatalystSelectQuery.Builder().selectAll().from("Todo").where(
//                    "no", ZCatalystUtil.Comparator.EQUAL_TO,
//                    no.toString()
//                ).build()
//                ZCatalystApp.getInstance().getDataStoreInstance().execute(query,
//                    success = {
//                        val toDo = parseTodoData(it)[0]
//                        isAddedDeferred.complete(toDo)
//                    },
//                    failure = {
//                        isAddedDeferred.completeExceptionally(it)
//                    })
                }
            },
            failure = {
                isAddedDeferred.completeExceptionally(it)
            }
        )
        return isAddedDeferred.await()
    }

    override suspend fun updateFilteredToDoList(userToDoList: UserToDoList): Boolean {

        val isUpdatedDeferred = CompletableDeferred<Boolean>()
        ZCatalystApp.getInstance().getDataStoreInstance().getTableInstance("ToDo")
            .getRow(userToDoList.rowId, success = { row ->
                row.setColumnValue("no", userToDoList.no)
                row.setColumnValue("text", userToDoList.text)
                row.setColumnValue("category", userToDoList.category)
                row.setColumnValue("status", userToDoList.status)
                row.update(
                    success = {
                        isUpdatedDeferred.complete(true)
                    },
                    failure = {
                        isUpdatedDeferred.completeExceptionally(it)
                    }
                )
            }, failure = {
                isUpdatedDeferred.completeExceptionally(it)
            })
        return isUpdatedDeferred.await()
    }

    override suspend fun deleteFilteredToDoList(userToDoList: UserToDoList): Boolean {

        val isDeletedDeferred = CompletableDeferred<Boolean>()
        ZCatalystApp.getInstance().getDataStoreInstance().getTableInstance("ToDo")
            .deleteRow(userToDoList.rowId,
                success = {
                    isDeletedDeferred.complete(true)
                }, failure = {})
        return isDeletedDeferred.await()
    }
}