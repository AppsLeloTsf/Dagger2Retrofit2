package com.molitics.molitician.ui.dashboard.register.location.adapter

import com.molitics.molitician.base.BaseViewHolder
import com.molitics.molitician.databinding.AdapterDistrictLocationBinding
import com.molitics.molitician.ui.dashboard.constantData.ConstantModel
import kotlinx.android.synthetic.main.adapter_district_location.view.*

class DistrictLocationViewHolder(mBinding: AdapterDistrictLocationBinding) :
    BaseViewHolder(mBinding.root) {
    var previousPosition = 0
    override fun onBind(position: Int, dataObject: Any?) {
        val constantModel = dataObject as ConstantModel

        with(itemView) {
            locationTextView.text = constantModel.key
            locationTextView.tag = constantModel
            locationTextView.isChecked = constantModel.isCheck
            locationTextView.setOnCheckedChangeListener { buttonView, isChecked ->
                previousPosition = position
            }
        }
    }
}