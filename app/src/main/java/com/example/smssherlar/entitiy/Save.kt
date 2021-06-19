package com.example.smssherlar.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Save {
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null
    @ColumnInfo(name="click")
    var click:Boolean?=null
    @ColumnInfo(name = "name_category")
    var category_name:String?=null
    @ColumnInfo(name = "name_")
    var name:String?=null
    var category_position:Int?=null
    @ColumnInfo(name="icon")
    var icon:Int?=null

    constructor(
        click: Boolean?,
        category_name: String?,
        name: String?,
        category_position: Int?,
        icon: Int?
    ) {
        this.click = click
        this.category_name = category_name
        this.name = name
        this.category_position = category_position
        this.icon = icon
    }
}