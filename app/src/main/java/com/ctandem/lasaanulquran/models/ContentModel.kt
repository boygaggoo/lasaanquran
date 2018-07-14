package com.ctandem.lasaanulquran.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "content")
class ContentModel {

    @PrimaryKey
    var contentId: Long = 0
    var contentTitle: String = ""
    var contentType: Long = 0
    var contentIndex: Long = 0
    var contentArray: String = ""
    var contentMedia: String = ""
    var isSaved: Boolean = false
    var font: Int = 0
}