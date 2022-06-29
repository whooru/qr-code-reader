package com.scanny.data.repositories.codes

import com.scanny.domain.entities.Volume
import com.scanny.domain.repository.CodesRepository
import kotlinx.coroutines.flow.Flow

class CodesRepositoryImpl(
    private val localDataSource: CodesLocalDataSource
): CodesRepository {
    override suspend fun getCodes(): Flow<List<Volume>> {
        return localDataSource.codes()
    }

    override suspend fun addCode(code: Volume) {
        localDataSource.addCode(code)
    }

    override suspend fun deleteCode(code: Volume) {
        localDataSource.deleteCode(code)
    }

    override suspend fun updateCode(code: Volume){
        localDataSource.updateCode(code)
    }

    override suspend fun getFavouritesCodes(): Flow<List<Volume>>{
        return localDataSource.favouritesCodes()
    }


}