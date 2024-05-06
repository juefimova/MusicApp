package com.example.musicapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RockType (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "singer")
    val singer: String,
    @ColumnInfo(name = "image")
    val image: String
    ): java.io.Serializable