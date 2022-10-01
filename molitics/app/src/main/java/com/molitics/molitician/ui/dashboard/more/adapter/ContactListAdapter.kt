package com.molitics.molitician.ui.dashboard.more.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.molitics.molitician.base.BaseViewHolder
import com.molitics.molitician.databinding.ContactItemAdapterBinding
import com.molitics.molitician.ui.dashboard.more.model.MyContactListModel

class ContactListAdapter : RecyclerView.Adapter<BaseViewHolder>() {
    internal var myContactListModel: MutableList<MyContactListModel> = mutableListOf()


    fun addContactList(contactListModel: List<MyContactListModel>) {
        myContactListModel.addAll(contactListModel)
        notifyDataSetChanged()
    }

    fun clearContactList() {
        myContactListModel.clear()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val itemView = ContactItemAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return myContactListModel.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position, myContactListModel)
    }


    inner class ViewHolder(private val mBinding: ContactItemAdapterBinding) : BaseViewHolder(mBinding.root) {

        override fun onBind(position: Int, dataObject: Any?) {

            this.mBinding.contactModel = myContactListModel[position]

            this.mBinding.executePendingBindings()
        }
    }
}
