package com.scanny.data.repositories.codes

import com.scanny.domain.entities.Volume
import kotlinx.coroutines.flow.Flow

interface CodesLocalDataSource {
    suspend fun addCode(code: Volume)
    suspend fun deleteCode(code: Volume)
    suspend fun updateCode(code: Volume)
    suspend fun codes(): Flow<List<Volume>>
    suspend fun favouritesCodes(): Flow<List<Volume>>
}