package com.example.vestirssreader.Data.Remote.Response


import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root


@Root(name = "channel", strict = false)
data class Channel(
    @field:Element(name = "about")
    @param:Element(name = "about")
    var about: String?=null,
    @field:Element(name = "description")
    @param:Element(name = "description")
    var description: String,
    @field:ElementList(entry = "item", inline = true)
    @param:ElementList(entry = "item", inline = true)
    val items: List<Item>? = null,
    @field:Element(name = "link")
    @param:Element(name = "link")
    var link: String?="",
    @field:Element(name = "title")
    @param:Element(name = "title")
    var title: String?=""
)