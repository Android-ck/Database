package com.zerir.sqldelight.data

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.zerir.sqldelight.AppDB
import com.zerir.sqldelight.PersonEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class PersonDataSource @Inject constructor(appDB: AppDB) {

    private val dispatcher: CoroutineContext = Dispatchers.IO
    private val queries = appDB.personEntityQueries

    suspend fun getPersonById(id: Long): PersonEntity? = withContext(dispatcher) {
        queries.getPersonById(id).executeAsOneOrNull()
    }

    fun getAllPersons(): Flow<List<PersonEntity>> = queries.getAllPersons().asFlow().mapToList()

    suspend fun deletePersonById(id: Long) = withContext(dispatcher) {
        queries.deletePersonById(id)
    }

    suspend fun insertPerson(
        firstName: String,
        lastName: String,
        id: Long? = null,
    ) = withContext(dispatcher) {
        queries.insertPerson(id, firstName = firstName, lastName = lastName)
    }
}