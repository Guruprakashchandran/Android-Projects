package com.example.todoapp.data.database

import androidx.compose.ui.graphics.vector.EmptyPath
import com.example.todoapp.Database
import com.example.todoapp.data.dataclasses.Categories
import com.example.todoapp.data.dataclasses.UserToDoList
import com.zoho.catalyst.api.ZCatalystRequest
import com.zoho.catalyst.api.ZCatalystResponse
import com.zoho.catalyst.common.ZCatalystUtil
import com.zoho.catalyst.datastore.ZCatalystRow
import com.zoho.catalyst.datastore.ZCatalystSelectQuery
import com.zoho.catalyst.setup.ZCatalystApp
import kotlinx.coroutines.CompletableDeferred

class ToDoListImpl(private val database: Database) : ToDoListInterface {

    private val queries = database.toDoListQueries
    override suspend fun insertToDo(
        toDoList: UserToDoList
    ): Any {
//        queries.insertTask(toDoList.task, toDoList.status, toDoList.category)
        val isFinishedDeferred = CompletableDeferred<Any>()
        val no = System.currentTimeMillis()
        val newData =
            ZCatalystApp.getInstance().getDataStoreInstance().getTableInstance("Todo").newRow()
        newData.setColumnValue("no", no)
        newData.setColumnValue("text", toDoList.text)
        newData.setColumnValue("category", toDoList.category)
        newData.setColumnValue("status", toDoList.status)

        newData.create(
            success = {
                toDoList.apply {
                    val toDo = UserToDoList(no = no, text = text, category = category, status = status, rowId = it.id, createdId = it.creatorId, createdTime = it.createdTime, modifiedTime = it.modifiedTime)
                    isFinishedDeferred.complete(toDo)
                }

//                val query = ZCatalystSelectQuery.Builder().selectAll().from("Todo").where(
//                    "no", ZCatalystUtil.Comparator.EQUAL_TO,
//                    no.toString()
//                ).build()
//                ZCatalystApp.getInstance().getDataStoreInstance().execute(query,
//                    success = {
//                        val toDo = parseTodoData(it)[0]
//                        isFinishedDeferred.complete(toDo)
//                    },
//                    failure = {
//                        isFinishedDeferred.completeExceptionally(it)
//                    })
            },
            failure = {
                isFinishedDeferred.completeExceptionally(it)
            },
        )
        return isFinishedDeferred.await()
    }

    override suspend fun getAllTodos(): List<UserToDoList> {

        val outputDeferred = CompletableDeferred<List<UserToDoList>>()
        val output = mutableListOf<UserToDoList>()
        val query = ZCatalystSelectQuery.Builder().selectAll().from("Todo").build()
        ZCatalystApp.getInstance().getDataStoreInstance().execute(query,
            success = {
                output.addAll(parseTodoData(it))
                outputDeferred.complete(output)
            },
            failure = {
                outputDeferred.completeExceptionally(it)
            })
//        return queries.getAllToDoList().asFlow().mapToList(Dispatchers.IO)
        return outputDeferred.await()
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


    override suspend fun updateToDo(toDoList: UserToDoList): Boolean {
//        queries.updateToDoListItemByStatus(toDoList.status, toDoList.no)


        val isUpdatedDeferred = CompletableDeferred<Boolean>()
        ZCatalystApp.getInstance().getDataStoreInstance().getTableInstance("Todo")
            .getRow(toDoList.rowId, { row ->
                row.setColumnValue("text", toDoList.text)
                row.setColumnValue("category", toDoList.category)
                row.setColumnValue("status", toDoList.status)
                row.update(success = {
                    println("Updated Successfully")
                    if (it.createdTime.isNotEmpty()) {
                        isUpdatedDeferred.complete(true)
                    }
                }, failure = {
                    isUpdatedDeferred.completeExceptionally(it)
                })
            }
            )
        return isUpdatedDeferred.await()
    }

    override suspend fun deleteDoTo(rowId: Long): Any {
        val isDeleteDeferred = CompletableDeferred<Any>()

        try {
            ZCatalystApp.getInstance().getDataStoreInstance().getTableInstance("ToDo")
                .deleteRow(rowId,
                    success = {
                        isDeleteDeferred.complete(true)
                    }, failure = {
                        isDeleteDeferred.completeExceptionally(it)
                    })
        } catch (e: Exception) {
            isDeleteDeferred.completeExceptionally(e)
        }
        return isDeleteDeferred.await()
    }

    override suspend fun getTodoBasedOnCategory(category: String): List<UserToDoList> {
        val data = ZCatalystSelectQuery.Builder().selectAll().from("Todo")
            .where("category", ZCatalystUtil.Comparator.EQUAL_TO, category).build()
        var output: List<UserToDoList> = emptyList()
        ZCatalystApp.getInstance().getDataStoreInstance().execute(data,
            success = {
                output = parseTodoData(it)
            }, failure = {

            }
        )
        return output
    }
}