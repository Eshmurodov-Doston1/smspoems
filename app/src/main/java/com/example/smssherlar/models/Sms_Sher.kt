package com.example.smssherlar.models

class Sms_Sher {
    var category:String?=null
    var name:String?=null
    var info:String?=null
    var category_position:Int?=null


    constructor()

    constructor(category: String?, name: String?, info: String?, category_position: Int?) {
        this.category = category
        this.name = name
        this.info = info
        this.category_position = category_position
    }


}