package com.molitics.molitician.ui.dashboard.register.location.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.molitics.molitician.base.BaseViewHolder
import com.molitics.molitician.databinding.AdapterLocationBinding
import com.molitics.molitician.ui.dashboard.constantData.ConstantModel
import com.molitics.molitician.ui.dashboard.register.location.LocationAdapterOnClick

class LocationAdapter(private val adapterOnClick: LocationAdapterOnClick) : RecyclerView.Adapter<BaseViewHolder>() {

    private var locationList: MutableList<ConstantModel> = mutableListOf()

    fun addData(locationList: List<ConstantModel>) {
        this.locationList.addAll(locationList)
        notifyDataSetChanged()
    }

    fun getData(position: Int) = locationList[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val mView = AdapterLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        mView.locationTextView.setOnClickListener {
            adapterOnClick.onClick(mView.locationTextView.tag as Int)
        }
        return LocationViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position, locationList[position])
    }
}