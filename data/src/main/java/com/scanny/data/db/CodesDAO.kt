package com.scanny.data.db

import androidx.room.*
import com.scanny.data.entities.CodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CodesDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveCode(code: CodeEntity)

    @Query("DELETE FROM code WHERE text = :text")
    fun deleteCode( text: String )

    @Query("SELECT * FROM code")
    fun getCodes(): Flow<List<CodeEntity>>

    @Query("UPDATE code SET favourite = :favourite WHERE text = :text")
    fun updateCode(text: String, favourite: Boolean)

    @Query("SELECT * FROM code WHERE favourite")
    fun getFavourites(): Flow<List<CodeEntity>>

}