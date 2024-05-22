package com.example.musicapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PopType (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "singer")
    val singer: String,
    @ColumnInfo(name = "image")
    val image: Int,
    @ColumnInfo(name = "number")
    val number: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "images")
    val images: String
): java.io.Serializable