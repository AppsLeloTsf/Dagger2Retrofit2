package com.molitics.molitician.ui.dashboard.channels.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.molitics.molitician.model.News

class ChannelBankModel {

    @SerializedName("data")
    @Expose
    lateinit var data: List<News>
}