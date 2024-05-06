package com.example.musicapp.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.musicapp.model.PersonMusic


@Dao
interface PersonMusicDao {
    @Query("SELECT * FROM personmusic")
    fun getAll(): List<PersonMusic>

    @Insert
    fun insertMusic(vararg songs: PersonMusic)

    @Query("SELECT * FROM PersonMusic WHERE user_id LIKE :user_id AND name LIKE :name LIMIT 1")
    fun getUserMusic(user_id: Long, name: String): List<PersonMusic>

    @Query("DELETE FROM personmusic WHERE user_id LIKE :user_id AND name LIKE :name")
    fun deleteMusicIDuserName(user_id: Long, name: String)



    @Delete
    fun deleteSong(book: PersonMusic)


}