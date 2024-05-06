package com.example.musicapp.model

import androidx.room.*
import com.example.musicapp.Music

@Dao
interface RoomMusicDao {
    @Query("SELECT * FROM roommusic")
    fun getAll(): List<RoomMusic>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSongs(vararg songs: RoomMusic)

    @Delete
    fun deleteSong(song: RoomMusic)
}