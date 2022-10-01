package com.molitics.molitician.ui.dashboard.register.location

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.molitics.molitician.BaseFragment
import com.molitics.molitician.R
import com.molitics.molitician.BR
import com.molitics.molitician.base.MyViewModelFactory
import com.molitics.molitician.databinding.FragmentLocationBinding
import com.molitics.molitician.ui.dashboard.ActivityFragment
import com.molitics.molitician.ui.dashboard.register.location.adapter.LocationAdapter
import com.molitics.molitician.ui.dashboard.register.register.viewModel.RegisterViewModel
import com.molitics.molitician.util.Constant
import kotlinx.android.synthetic.main.fragment_location.*
import javax.inject.Inject
import javax.inject.Provider

class LocationFragment : BaseFragment<FragmentLocationBinding, RegisterViewModel>(), LocationAdapterOnClick {

    @Inject
    lateinit var factory: MyViewModelFactory<RegisterViewModel>

    @Inject
    lateinit var locationAdapter: LocationAdapter

    @Inject
    lateinit var mLayoutManager: Provider<LinearLayoutManager>

    private val locationFragmentViewModel: RegisterViewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[RegisterViewModel::class.java]
    }

    override fun getBindingVariable(): Int = BR.locationViewModel

    override fun getLayoutId(): Int = R.layout.fragment_location

    override fun getViewModel(): RegisterViewModel = locationFragmentViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindAdapter()
        setObservable()
        locationFragmentViewModel.getStateLocationList(arguments?.getInt(Constant.IntentKey.STATE_ID))
    }

    private fun setObservable() {
        locationFragmentViewModel.locationList.observe(this, Observer {
            it?.let {
                locationFragmentViewModel.setLocationData(it)
            }
        })
    }

    private fun bindAdapter() {
        locationRecycler.apply {
            layoutManager = mLayoutManager.get()
            adapter = locationAdapter
        }
    }

    companion object {
        fun getInstance(from: String, stateId: Int = 0): LocationFragment {
            val locationFragment = LocationFragment()
            val mBundle = Bundle()
            mBundle.putString(Constant.IntentKey.FROM, from)
            mBundle.putInt(Constant.IntentKey.STATE_ID, stateId)
            locationFragment.arguments = mBundle
            return locationFragment
        }
    }

    override fun onClick(id: Any) {
        (activity as ActivityFragment).replaceFragment(
                DistrictLocationFragment.getInstance(Constant.From.DISTRICT_LOCATION, id as Int),
                null, true, false)
    }
}