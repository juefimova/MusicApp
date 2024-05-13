package com.example.loginapp.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.musicapp.model.*


@Database(entities = [
    PersonMusic::class, RoomMusic::class, RoomUser::class,
    RockType:: class, PopType:: class, RapType:: class, RoomUserFriends:: class
    ], version = 1
    )
abstract class AppDataBase : RoomDatabase() {

    abstract fun personMusicDao(): PersonMusicDao

    abstract fun roomMusicDao(): RoomMusicDao

    abstract fun roomUserDao(): RoomUserDao

    abstract fun rockTypeDao(): RockTypeDao

    abstract fun popTypeDao(): PopTypeDao

    abstract fun rapTypeDao(): RapTypeDao

    abstract fun roomUserFriendsDao(): RoomUserFriendsDao

}