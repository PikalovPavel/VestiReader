package com.example.vestirssreader.Util

import android.util.Log
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*


fun parseDate(pubDate: String): String {
    //val formatter = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss +zzzz")
   // val date = formatter.parse(pubDate)?: Date()
   // val cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"))
   // cal.time = date
    val dates = pubDate.split(" ")

//    val day = cal.get(Calendar.DAY_OF_MONTH)
//    val month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale("ru"));
//    val year = cal.get(Calendar.YEAR)
//    val hour = parseTime(cal.get(Calendar.HOUR))
//    val minute = parseTime(cal.get(Calendar.MINUTE))
    val month = getMonth(dates[2])
    return "${dates[1]} $month ${dates[3]} ${dates[4].substring(0,5)}"
}


//replace 2 empty lines to 1 empty line

fun removeLines(text:String):String {
    val pattern = "\n{3,}".toRegex()
    return text.replace(pattern, "\n\n")
}

fun getMonth(month: String): String {
    val date = SimpleDateFormat("MMM", Locale.ENGLISH).parse(month)
    val cal = Calendar.getInstance()
    cal.time = date

    return cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale("ru"));

}