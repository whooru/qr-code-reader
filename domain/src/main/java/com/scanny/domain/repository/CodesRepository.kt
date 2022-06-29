package com.scanny.domain.repository

import com.scanny.domain.entities.Volume
import kotlinx.coroutines.flow.Flow

interface CodesRepository {

    suspend fun getCodes(): Flow<List<Volume>>

    suspend fun addCode(code: Volume)

    suspend fun deleteCode(code: Volume)

    suspend fun updateCode(code: Volume)

    suspend fun getFavouritesCodes(): Flow<List<Volume>>
}