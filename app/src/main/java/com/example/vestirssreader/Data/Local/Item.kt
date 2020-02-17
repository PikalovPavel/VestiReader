package com.example.vestirssreader.Data.Local


import android.os.Parcel
import android.os.Parcelable
import com.example.vestirssreader.Util.parseDate
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root;



@Root(name = "item", strict = false)
data class Item @JvmOverloads constructor (
    @field:Element(name = "category")
    @param:Element(name = "category")
    var category: String?="",
    @field:Element(name = "description")
    @param:Element(name = "description")
    var description: String?="",
    @field:Path("enclosure")
    @field:Attribute(name="height", required = false)
    @param:Path("enclosure")
    @param:Attribute(name="height", required = false)
    var height: String?="",
    @field:Path("enclosure")
    @field:Attribute(name="width", required = false)
    @param:Path("enclosure")
    @param:Attribute(name="width", required = false)
    var width: String?="",
    @field:Path("enclosure")
    @field:Attribute(name="type", required = false)
    @param:Path("enclosure")
    @param:Attribute(name="type", required = false)
    var type: String?="",
    @field:Path("enclosure")
    @field:Attribute(name="url", required = false)
    @param:Path("enclosure")
    @param:Attribute(name="url", required = false)
    var url: String?="",
    @field:Element(name = "full-text")
    @param:Element(name = "full-text")
    var fullText: String?="",
    @field:Element(name = "link")
    @param:Element(name = "link")
    var link: String?="",
    @field:Element(name = "pubDate")
    @param:Element(name = "pubDate")
    var pubDate: String?="",
    @field:Element(name = "title")
    @param:Element(name = "title")
    var title: String?=""
) {
    fun mapToNewsItem() =
        NewsItem(category = category,
            link = url,
            fullText = fullText,
            pubDate = parseDate(pubDate!!),
            title = title)


}


class NewsItem(
    val category: String?,
    val link: String?,
    val fullText: String?,
    var pubDate: String?,
    var title: String?
):Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(category)
        parcel.writeString(link)
        parcel.writeString(fullText)
        parcel.writeString(pubDate)
        parcel.writeString(title)
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
}
