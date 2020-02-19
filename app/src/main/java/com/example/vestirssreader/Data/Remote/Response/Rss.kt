package com.example.vestirssreader.Data.Remote.Response


import org.simpleframework.xml.Element
import org.simpleframework.xml.Root


@Root(name = "rss", strict = false)
data class Rss(
    @field:Element(name = "channel")
    @param:Element(name = "channel")
    val channel: Channel? = null)