package com.molitics.molitician.ui.dashboard.channels.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.molitics.molitician.ui.dashboard.hotTopics.model.HotTopicModel

class ChannelDataModel {


    @SerializedName("data")
    @Expose
    lateinit var channels: List<HotTopicModel>
}