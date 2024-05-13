package com.example.musicapp.model

import androidx.room.*

@Dao
interface PopTypeDao {
    @Query("SELECT * FROM poptype")
    fun getAll(): List<PopType>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSongs(vararg songs: PopType)

    @Delete
    fun deleteSong(song: PopType)
}