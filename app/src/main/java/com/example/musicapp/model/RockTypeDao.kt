package com.example.musicapp.model

import androidx.room.*

@Dao
interface RockTypeDao {
    @Query("SELECT * FROM rocktype")
    fun getAll(): List<RockType>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSongs(vararg songs: RockType)

    @Delete
    fun deleteSong(song: RockType)
}