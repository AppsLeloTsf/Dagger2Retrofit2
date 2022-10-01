package com.molitics.molitician.ui.dashboard.liveVideo.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.molitics.molitician.model.News
import com.molitics.molitician.model.NewsVideoModel
import java.util.ArrayList

class MoliticsVideoModel {


    @SerializedName("current_page")
    @Expose
    val current_page: Int? = null

    @SerializedName("data")
    @Expose
    val moliticsLiveVideos: ArrayList<News>? = null

}