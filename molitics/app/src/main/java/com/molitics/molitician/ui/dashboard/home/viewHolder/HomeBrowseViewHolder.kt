package com.molitics.molitician.ui.dashboard.home.viewHolder

import android.view.View
import androidx.core.content.ContextCompat
import com.molitics.molitician.R
import com.molitics.molitician.base.BaseViewHolder
import com.molitics.molitician.databinding.AdapterHomeBrowseBinding
import com.molitics.molitician.ui.dashboard.home.model.HomeBrowseModel
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import kotlinx.android.synthetic.main.adapter_home_browse.view.*

class HomeBrowseViewHolder(private val mBinding: AdapterHomeBrowseBinding) : BaseViewHolder(mBinding.root) {

    override fun onBind(position: Int, dataObject: Any?) {
        val data = dataObject as HomeBrowseModel
        with(itemView) {

            if (data.image.isNotEmpty()) {
                Picasso.with(itemView.context).load(data.image).into(itemImageView)
            }

            titleView.text = data.key
            flMain.tag = data.value
            if (data.isCheck) {
                flMain.background = ContextCompat.getDrawable(context, R.drawable.edittxt_boundary_white)
                downArrow.visibility = View.VISIBLE
            } else {
                downArrow.visibility = View.GONE
                flMain.background = null
            }
        }
    }
}