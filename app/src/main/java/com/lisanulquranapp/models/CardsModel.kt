package com.lisanulquranapp.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Nauman on 12/5/2017.
 */
class CardsModel() : Parcelable{
    var id: Long = 0
    var words: String = "Abc"
    var meanings: String = "Abc"
    var detail: String = "Abc"
    var fontQuestion: Int = 0
    var fontAnswer: Int = 0
    var fontDetail: Int = 0

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        words = parcel.readString()
        meanings = parcel.readString()
        detail = parcel.readString()
        fontQuestion = parcel.readInt()
        fontAnswer = parcel.readInt()
        fontDetail = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(words)
        parcel.writeString(meanings)
        parcel.writeString(detail)
        parcel.writeInt(fontQuestion)
        parcel.writeInt(fontAnswer)
        parcel.writeInt(fontDetail)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CardsModel> {
        override fun createFromParcel(parcel: Parcel): CardsModel {
            return CardsModel(parcel)
        }

        override fun newArray(size: Int): Array<CardsModel?> {
            return arrayOfNulls(size)
        }
    }
}