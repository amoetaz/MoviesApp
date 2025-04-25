package com.moetaz.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moetaz.data.Constants.MOVIES_TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM $MOVIES_TABLE_NAME")
    fun getAllData(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items : List<MovieEntity>)

    @Query("DELETE FROM $MOVIES_TABLE_NAME")
    suspend fun deleteAll()
}