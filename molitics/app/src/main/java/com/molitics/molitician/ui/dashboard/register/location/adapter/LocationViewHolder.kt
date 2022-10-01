package com.molitics.molitician.ui.dashboard.register.location.adapter

import com.molitics.molitician.base.BaseViewHolder
import com.molitics.molitician.databinding.AdapterLocationBinding
import com.molitics.molitician.ui.dashboard.constantData.ConstantModel
import kotlinx.android.synthetic.main.adapter_location.view.*

class LocationViewHolder(mBinding: AdapterLocationBinding) : BaseViewHolder(mBinding.root) {

    override fun onBind(position: Int, dataObject: Any?) {
        val constantModel = dataObject as ConstantModel

        with(itemView) {
            locationTextView.text = constantModel.key
            locationTextView.tag = constantModel.value
        }
    }
}