package com.tsfapps.rxjavaretrofitpractice.network


data class BookListModel(val items: ArrayList<VolumeInfo>)
data class VolumeInfo(val volumeInfo: BookInfo)
data class BookInfo(val title: String, val publisher: String, val description: String, val imageLinks: ArrayList<ImageLinks> )
data class ImageLinks(val smallThumbnail: String)