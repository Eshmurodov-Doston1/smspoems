package com.example.smssherlar.models

import java.io.Serializable

class Category:Serializable {
    var id:Int?=null
    var name_category:String?=null

    constructor()
    constructor(id: Int?, name_category: String?) {
        this.id = id
        this.name_category = name_category
    }


}