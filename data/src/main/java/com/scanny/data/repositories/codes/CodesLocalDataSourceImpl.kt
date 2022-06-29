package com.scanny.data.repositories.codes

import com.scanny.data.db.CodesDAO
import com.scanny.data.mappers.CodeEntityMapper
import com.scanny.domain.entities.Volume
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CodesLocalDataSourceImpl(
    private val bookDao: CodesDAO,
    private val dispatcher: CoroutineDispatcher,
    private val codeEntityMapper: CodeEntityMapper
) : CodesLocalDataSource {
    override suspend fun addCode(code: Volume) = withContext(dispatcher) {
        bookDao.saveCode(codeEntityMapper.toCodeEntity(code))
    }

    override suspend fun deleteCode(code: Volume) = withContext(dispatcher) {
        bookDao.deleteCode(code.volumeInfo.text)
    }

    override suspend fun updateCode(code: Volume) = withContext(dispatcher) {
//        bookDao.updateCode(codeEntityMapper.toCodeEntity(code))
        bookDao.updateCode(code.volumeInfo.text, code.volumeInfo.favourite)
    }

    override suspend fun favouritesCodes(): Flow<List<Volume>> {
        val savedCodesFlow = bookDao.getFavourites()
        return savedCodesFlow.map { list ->
            list.map { element ->
                codeEntityMapper.toVolume(element)
            }
        }
    }

    override suspend fun codes(): Flow<List<Volume>> {
        val codesFlow = bookDao.getCodes()
        return codesFlow.map { list ->
            list.map { element ->
                codeEntityMapper.toVolume(element)
            }
        }
    }
}