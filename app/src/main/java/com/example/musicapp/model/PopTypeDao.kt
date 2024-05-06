package com.example.musicapp.model

import androidx.room.*

@Dao
interface PopTypeDao {
    @Query("SELECT * FROM rocktype")
    fun getAll(): List<RockType>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSongs(vararg songs: RoomMusic)

    @Delete
    fun deleteSong(song: RoomMusic)
}