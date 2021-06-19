package com.example.smssherlar.dao

import androidx.room.*
import com.example.smssherlar.entitiy.Save
import io.reactivex.Flowable

@Dao
interface SaveDao {
    @Insert
    fun addSaveInformation(save:Save)

    @Update
    fun updateSaveInformation(save: Save)

    @Delete
    fun delete(save: Save)


    @Query("select*from save where name_=:name and  category_position=:category_pos")
    fun getSaveInformations1(name:String,category_pos:Int):Save

    @Query("select*from save where name_=:name and category_position=:category_pos")
    fun getSaveInformations(name:String,category_pos:Int):Flowable<Save>

    @Query("select*from save where icon=:icon")
    fun getSaveInformations2(icon:Int):Flowable<List<Save>>
}