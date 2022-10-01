package com.molitics.molitician.ui.dashboard.register.location.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.molitics.molitician.base.BaseViewHolder
import com.molitics.molitician.databinding.AdapterDistrictLocationBinding
import com.molitics.molitician.ui.dashboard.constantData.ConstantModel
import com.molitics.molitician.ui.dashboard.register.location.LocationAdapterOnClick
import kotlinx.android.synthetic.main.adapter_district_location.view.*

class DistrictLocationAdapter(private val adapterOnClick: LocationAdapterOnClick) :
    RecyclerView.Adapter<BaseViewHolder>() {

    private var locationList: MutableList<ConstantModel> = mutableListOf()

    fun addData(locationList: List<ConstantModel>) {
        this.locationList.addAll(locationList)
        notifyDataSetChanged()
    }

    fun getData(position: Int) = locationList[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val mView = AdapterDistrictLocationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        mView.locationTextView.setOnClickListener { view ->
            locationList.map { loc -> loc.isCheck = false }
            val constantModel = mView.locationTextView.tag as ConstantModel
            adapterOnClick.onClick(constantModel.value)
            notifyDataSetChanged()
            val tempRadio = view as RadioButton

            locationList.find { it == constantModel }?.isCheck = tempRadio.isChecked
        }
        return DistrictLocationViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position, locationList[position])
    }
}