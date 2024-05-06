package com.example.musicapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PersonMusic (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "user_id")
    val user_id: Long,
    @ColumnInfo(name = "name")
    val name: String
): java.io.Serializable