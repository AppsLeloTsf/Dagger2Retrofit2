package com.molitics.molitician.ui.dashboard.nationalNews.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class HomeElectionModel {

    @SerializedName("title")
    @Expose
    var title: String? = null


    @SerializedName("date")
    @Expose
    var date: String? = null


    @SerializedName("election_id")
    @Expose
    var electionId: Int? = null

    @SerializedName("state_id")
    @Expose
    var stateId: Int? = null

}