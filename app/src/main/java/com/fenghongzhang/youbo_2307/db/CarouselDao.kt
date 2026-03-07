package com.fenghongzhang.youbo_2307.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CarouselDao {

    @Query("SELECT * FROM carousel_item ORDER BY id ASC")
    fun getAllFlow(): Flow<List<CarouselItemEntity>>

    @Query("SELECT * FROM carousel_item ORDER BY id ASC")
    suspend fun getAll(): List<CarouselItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<CarouselItemEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: CarouselItemEntity)

    @Query("DELETE FROM carousel_item")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM carousel_item")
    suspend fun count(): Int
}
