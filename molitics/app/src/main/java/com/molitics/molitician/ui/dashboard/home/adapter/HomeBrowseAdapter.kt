package com.molitics.molitician.ui.dashboard.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.molitics.molitician.base.BaseViewHolder
import com.molitics.molitician.databinding.AdapterHomeBrowseBinding
import com.molitics.molitician.ui.dashboard.home.interfacess.HomeBrowserClickListener
import com.molitics.molitician.ui.dashboard.home.model.HomeBrowseModel
import com.molitics.molitician.ui.dashboard.home.viewHolder.HomeBrowseViewHolder

class HomeBrowseAdapter : RecyclerView.Adapter<BaseViewHolder>() {
    var browseList = arrayListOf<HomeBrowseModel>()

    lateinit var browserClickListener: HomeBrowserClickListener

    fun addData(browseList: List<HomeBrowseModel>) {

        this.browseList = browseList as ArrayList<HomeBrowseModel>
        notifyDataSetChanged()
    }

    fun setInterface(browserClickListener: HomeBrowserClickListener) {
        this.browserClickListener = browserClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = AdapterHomeBrowseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        view.flMain.setOnClickListener {

            handleItemClick(view.flMain.tag as Int)
        }
        return HomeBrowseViewHolder(view)
    }

    override fun getItemCount(): Int = browseList.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(holder.adapterPosition, browseList[position])
    }

    private fun handleItemClick(id: Int) {
        val previousItem = browseList.find { it.isCheck }
        previousItem?.isCheck = false
        val singleItem = browseList.single { it.value == id }
        singleItem.isCheck = true
        notifyDataSetChanged()
        browserClickListener.onBrowseClick(id)
    }
}