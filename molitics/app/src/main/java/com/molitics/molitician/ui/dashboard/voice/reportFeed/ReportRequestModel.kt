package com.molitics.molitician.ui.dashboard.voice.reportFeed

data class ReportRequestModel(val reported_by_id: Int,val reason_id:Int, val feed_id: Int? = null, val reported_for_id: Int? = null)