package com.example.smssherlar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.smssherlar.dao.SaveDao
import com.example.smssherlar.entitiy.Save

@Database(entities = [Save::class],version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun saveDao():SaveDao
    companion object{
        var instanse:AppDatabase?=null
        @Synchronized
        fun getInstance(context: Context):AppDatabase{
            instanse = Room.databaseBuilder(context,AppDatabase::class.java,"MySave.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()

            return instanse!!
        }
    }
}