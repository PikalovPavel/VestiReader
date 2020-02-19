package com.example.vestirssreader.Data.Database.Enity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.vestirssreader.Data.Database.DateConverter
import com.example.vestirssreader.Util.readDate
import com.example.vestirssreader.Util.writeDate

import java.util.*


@Entity(tableName = "newsitem")
@TypeConverters(DateConverter::class)
data class NewsItem(
    @ColumnInfo(name = "category")
    var category: String,
    @ColumnInfo(name = "link")
    @PrimaryKey
    var link: String,
    @ColumnInfo(name = "fullText")
    var fullText: String,
    @ColumnInfo(name =  "title")
    var title: String,
    @ColumnInfo(name = "date")
    var date: Date
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readDate()?:Date()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(category)
        parcel.writeString(link)
        parcel.writeString(fullText)
        parcel.writeString(title)
        parcel.writeDate(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsItem> {
        override fun createFromParcel(parcel: Parcel): NewsItem {
            return NewsItem(parcel)
        }

        override fun newArray(size: Int): Array<NewsItem?> {
            return arrayOfNulls(size)
        }
    }
    fun printDate():String {
        val cal = Calendar.getInstance()
        cal.time = date
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale("ru"))
        val year = cal.get(Calendar.YEAR)
        val hours = getTime(cal.get(Calendar.HOUR))
        val minutes = getTime(cal.get(Calendar.MINUTE))
        return "$day $month $year $hours:$minutes"
    }
    private fun getTime(time:Int):String {
        return if (time< 10) "0$time"
           else time.toString()
    }
}


