package com.molitics.molitician.ui.dashboard.voice.reportFeed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.molitics.molitician.base.BaseViewHolder
import com.molitics.molitician.databinding.ReportAdapterBinding

class ReportAdapter(private val reportClick: (Int) -> Unit) : RecyclerView.Adapter<BaseViewHolder>() {

    var adapterList = listOf<ReportReasonResponseModel>()

    fun setData(adapterList: List<ReportReasonResponseModel>) {
        this.adapterList = adapterList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val itemView: ReportAdapterBinding = ReportAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        itemView.titleTextView.setOnClickListener {
            reportClick.invoke(it.tag as Int)
        }
        return ReportViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position, adapterList[position])
    }

    override fun getItemCount(): Int = adapterList.size
}