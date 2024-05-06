package com.example.todoapp.data.database

import com.example.todoapp.Database
import com.example.todoapp.data.dataclasses.Categories
import com.example.todoapp.data.repository.ToDoListRepository
import com.zoho.catalyst.datastore.ZCatalystSelectQuery
import com.zoho.catalyst.setup.ZCatalystApp
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext

class ToDoCategoryImpl(private val database: Database) : ToDoCategoryInterface {

//    val queries = database.toDoCategoryQueries
    var repository: ToDoListRepository? = null
    override suspend fun addCategory(category: Categories): Boolean {

        val query =
            ZCatalystApp.getInstance().getDataStoreInstance().getTableInstance("Category").newRow()
        query.setColumnValue("Category", category.name)
        var isAdded: Boolean
        val isAddedDeferred = CompletableDeferred<Boolean>()
        query.create(
            success = {
                println("New Category Created!!!")
                isAdded = true
                isAddedDeferred.complete(isAdded)
            },
            failure = {
                println("New Category doesn't Created!!!")
            }
        )
        return isAddedDeferred.await()
    }


    override suspend fun deleteCategory(category: Categories) {

        ZCatalystApp.getInstance().getDataStoreInstance().getTableInstance("Category")
            .deleteRow(category.createdId,
                success = {},
                failure = {}
            )
//        queries.deleteCategory(toDoCategory.category)
    }

    override suspend fun getAllCategories(): List<Categories> {
        val query = ZCatalystSelectQuery.Builder().selectAll().from("Category").build()
        val output = ArrayList<Categories>()
        val outputDeferred = CompletableDeferred<List<Categories>>()
        try {
            ZCatalystApp.getInstance().getDataStoreInstance()
                .execute(query,
                    success = {
                        output.addAll(parseString(it))
                        println("Used For Success: $output")
                        outputDeferred.complete(output)
                    },
                    failure = {

                    }
                )
        } catch (e: Exception) {

        }
        return outputDeferred.await()
//        return queries.getAllCategories().asFlow().mapToList(Dispatchers.IO)
    }

    private fun parseString(it: List<Map<String, Map<String, Any?>>>): List<Categories> {

        val output: ArrayList<Categories> = ArrayList()
        for (i in it.indices) {
            it[i].values.forEach {

                output.add(
                    Categories(
                        name = it["Category"]?.toString(),
                        createdTime = it["CREATEDTIME"]?.toString(),
                        modifiedTime = it["MODIFIEDTIME"]?.toString(),
                        createdId = it["CREATORID"]?.toString()!!.toLong(),
                        rowId = it["ROWID"].toString().toLong()
                    )
                )
            }
        }
        return output
    }
}