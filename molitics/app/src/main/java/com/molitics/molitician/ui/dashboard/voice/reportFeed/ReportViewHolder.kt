package com.molitics.molitician.ui.dashboard.voice.reportFeed

import com.molitics.molitician.base.BaseViewHolder
import com.molitics.molitician.databinding.ReportAdapterBinding
import kotlinx.android.synthetic.main.report_adapter.view.*

class ReportViewHolder(mBinding: ReportAdapterBinding) : BaseViewHolder(mBinding.root) {

    override fun onBind(position: Int, dataObject: Any?) {
        val data = dataObject as ReportReasonResponseModel
        with(itemView) {
            titleTextView.text = data.reason
            titleTextView.tag = data.id
        }
    }
}