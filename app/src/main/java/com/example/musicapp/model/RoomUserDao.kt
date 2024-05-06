package com.example.musicapp.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RoomUserDao {
    @Query("SELECT * FROM roomuser")
    fun getAll(): List<RoomUser>

    @Query("SELECT * FROM roomuser WHERE id = :userID")
    fun loadAllByID(userID: Long): RoomUser

    @Query("SELECT * FROM roomuser WHERE email LIKE :first AND password LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): RoomUser

    @Query("UPDATE roomuser SET email = :email, password = :password WHERE id = :currentId")
    fun update(email: String?, password: String?, currentId: Long)

    @Insert
    fun insertAll(vararg users: RoomUser)

    @Delete
    fun delete(user: RoomUser)
}