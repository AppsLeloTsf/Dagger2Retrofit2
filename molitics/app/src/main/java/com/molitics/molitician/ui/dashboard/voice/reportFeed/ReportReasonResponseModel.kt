package com.molitics.molitician.ui.dashboard.voice.reportFeed

import io.realm.internal.Keep

@Keep
data class ReportReasonResponseModel(val id: Int, val reason: String, val order: Int)