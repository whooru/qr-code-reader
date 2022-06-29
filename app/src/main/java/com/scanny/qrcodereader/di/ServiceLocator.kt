package com.scanny.qrcodereader.di

import android.content.Context
import com.scanny.data.db.CodesDatabase
import com.scanny.data.mappers.CodeEntityMapper
import com.scanny.data.repositories.codes.CodesLocalDataSource
import com.scanny.data.repositories.codes.CodesLocalDataSourceImpl
import com.scanny.data.repositories.codes.CodesRepositoryImpl
import kotlinx.coroutines.Dispatchers

object ServiceLocator {

    private var database: CodesDatabase? = null

    private val codeEntityMapper by lazy {
        CodeEntityMapper()
    }

    @Volatile
    var codesRepository: CodesRepositoryImpl? = null

    fun provideCodesRepository(context: Context): CodesRepositoryImpl {
        synchronized(this) {
            return codesRepository ?: createCodesRepository(context)
        }
    }

    private fun createCodesRepository(context: Context): CodesRepositoryImpl {
        val newRepo = CodesRepositoryImpl(
            createCodesLocalDataSource(context)
        )
        codesRepository = newRepo
        return codesRepository as CodesRepositoryImpl
    }

    private fun createCodesLocalDataSource(context: Context): CodesLocalDataSource {
        val database = database?: createDatabase(context)
        return CodesLocalDataSourceImpl(database.codesDao(), Dispatchers.IO, codeEntityMapper)
    }

    private fun createDatabase(context: Context): CodesDatabase {
        val result = CodesDatabase.getDatabase(context)
        database = result
        return result
    }


}