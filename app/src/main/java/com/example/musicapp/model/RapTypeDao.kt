package com.example.musicapp.model

import androidx.room.*

@Dao
interface RapTypeDao {
    @Query("SELECT * FROM raptype")
    fun getAll(): List<RapType>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSongs(vararg songs: RapType)

    @Delete
    fun deleteSong(song: RapType)
}