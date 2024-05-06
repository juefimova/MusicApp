package com.example.musicapp.model

import androidx.room.*

@Dao
interface RapTypeDao {
    @Query("SELECT * FROM rocktype")
    fun getAll(): List<RockType>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSongs(vararg songs: RoomMusic)

    @Delete
    fun deleteSong(song: RoomMusic)
}