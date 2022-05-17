package com.example.domain.interfaces

import kotlinx.coroutines.flow.Flow

interface DatabaseRepository<T> {
    fun getDataList(): Flow<List<T>>
    fun getByUid(uid: String): T?
    fun deleteByUid(uid: String)
    fun insertAll(vararg obj: T)
    fun insertAll(objects: List<T>)
    fun update(obj: T)
}