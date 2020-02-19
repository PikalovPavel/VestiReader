package com.example.vestirssreader.Util

import android.os.Parcel
import java.text.SimpleDateFormat
import java.util.*







fun parseDate(pubDate: String): Date{
    //val formatter = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss +zzzz")
   // val date = formatter.parse(pubDate)?: Date()

    val dates = pubDate.split(" ")
//    val monthInt = getMonthInt(dates[2])
//    val month = getMonth(dates[2])
    val dateToPrint =  "${dates[1]} ${dates[2]} ${dates[3]} ${dates[4].substring(0,5)}"
    val fullDate = SimpleDateFormat("dd MMM yyyy HH:mm", Locale("en")).parse(dateToPrint)
    return fullDate
}




//replace 2 empty lines to 1 empty line

fun removeLines(text:String):String {
    val pattern = "\n{3,}".toRegex()
    return text.replace(pattern, "\n\n")
}

fun getMonth(month: String): String {
    val date = SimpleDateFormat("MMM", Locale.ENGLISH).parse(month)
    val cal = Calendar.getInstance()
    cal.time = date!!
    return cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale("ru"))!!
}

fun getMonthInt(month: String): Int {
    val date = SimpleDateFormat("MMM", Locale.ENGLISH).parse(month)
    val cal = Calendar.getInstance()
    cal.time = date!!
    return cal.get(Calendar.MONTH)
}


fun Parcel.writeDate(date: Date?) {
    writeLong(date?.time ?: -1)
}

fun Parcel.readDate(): Date? {
    val long = readLong()
    return if (long != -1L) Date(long) else null
}
