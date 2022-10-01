package com.molitics.molitician.ui.dashboard.register.location

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.molitics.molitician.BR
import com.molitics.molitician.BaseFragment
import com.molitics.molitician.R
import com.molitics.molitician.base.MyViewModelFactory
import com.molitics.molitician.databinding.FragmentDisctrictLocationBinding
import com.molitics.molitician.ui.dashboard.DashBoardActivity
import com.molitics.molitician.ui.dashboard.register.location.adapter.DistrictLocationAdapter
import com.molitics.molitician.ui.dashboard.register.register.viewModel.RegisterViewModel
import com.molitics.molitician.util.Constant
import com.molitics.molitician.util.PrefUtil
import kotlinx.android.synthetic.main.fragment_location.*
import javax.inject.Inject

class DistrictLocationFragment :
    BaseFragment<FragmentDisctrictLocationBinding, RegisterViewModel>(), LocationAdapterOnClick {

    @Inject
    lateinit var factory: MyViewModelFactory<RegisterViewModel>

    @Inject
    lateinit var locationAdapter: DistrictLocationAdapter

    @Inject
    lateinit var mLayoutManager: LinearLayoutManager

    private val locationFragmentViewModel: RegisterViewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[RegisterViewModel::class.java]
    }

    override fun getBindingVariable(): Int = BR.districtLocationViewModel

    override fun getLayoutId(): Int = R.layout.fragment_disctrict_location

    override fun getViewModel(): RegisterViewModel = locationFragmentViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindAdapter()
        setObservable()
        locationFragmentViewModel.getStateLocationList(arguments?.getInt(Constant.IntentKey.STATE_ID))
    }

    private fun setObservable() {
        locationFragmentViewModel.districtList.observe(this, Observer {
            locationFragmentViewModel.setDistrictLocationData(it)
        })
        locationFragmentViewModel.registerData.observe(this, Observer {
            PrefUtil.putString(Constant.PreferenceKey.NEXT_STRING, Constant.IntentKey.HOME)

            startActivity(Intent(requireContext(), DashBoardActivity::class.java))
            requireActivity().finish()
        })
    }

    private fun bindAdapter() {
        locationRecycler.apply {
            layoutManager = mLayoutManager
            adapter = locationAdapter
        }
    }

    companion object {
        fun getInstance(from: String, stateId: Int = 0): DistrictLocationFragment {
            val locationFragment = DistrictLocationFragment()
            val mBundle = Bundle()
            mBundle.putString(Constant.IntentKey.FROM, from)
            mBundle.putInt(Constant.IntentKey.STATE_ID, stateId)
            locationFragment.arguments = mBundle
            return locationFragment
        }
    }

    override fun onClick(item: Any) {
        locationFragmentViewModel.districtId = item as Int
    }
}