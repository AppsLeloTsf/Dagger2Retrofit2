package com.molitics.molitician.ui.dashboard.register.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*

import com.google.android.gms.tasks.Task
import com.molitics.molitician.BR
import com.molitics.molitician.BaseFragment
import com.molitics.molitician.R
import com.molitics.molitician.base.MyViewModelFactory
import com.molitics.molitician.databinding.FragmentLocationBinding
import com.molitics.molitician.httpapi.RetrofitRestClient
import com.molitics.molitician.ui.dashboard.ActivityFragment
import com.molitics.molitician.ui.dashboard.constantData.ConstantModel
import com.molitics.molitician.ui.dashboard.register.location.adapter.LocationAdapter
import com.molitics.molitician.ui.dashboard.register.register.viewModel.RegisterViewModel
import com.molitics.molitician.util.Constant
import com.molitics.molitician.util.Util.showToast
import kotlinx.android.synthetic.main.fragment_disctrict_location.*
import kotlinx.android.synthetic.main.fragment_location.*
import kotlinx.android.synthetic.main.fragment_location.locationRecycler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

class LocationFragment : BaseFragment<FragmentLocationBinding, RegisterViewModel>(), LocationAdapterOnClick {


    private val PERMISSION_ID = 1000;


    @Inject
    lateinit var factory: MyViewModelFactory<RegisterViewModel>

    @Inject
    lateinit var locationAdapter: LocationAdapter

    @Inject
    lateinit var mLayoutManager: Provider<LinearLayoutManager>



    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    private var mState : String = ""
    private var mCity : String = ""
    private var mCityId : Int = -1
    private var mStateId : Int = -1

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

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(baseActivity)

        getLastLocation()


        locationBtn.setOnClickListener{


            Log.d("TSF_APPS", "LocationBtn clicked state  $mState \tcity: $mCity");
            mStateId = com.molitics.molitician.util.Location.getStateId(mState);
            GlobalScope.launch {
                Log.d("TSF_APPS", "LocationBtn clicked stateId: $mStateId \tcityId: $mCityId");
                callDistrictApi(mStateId, mCity);

                locationFragmentViewModel.setAutoStateAndDistrictId(mStateId,mCityId,true,activity);
                locationFragmentViewModel.doneButtonClicked();
            }
        }
        ivBackStateList.setOnClickListener{
            val fm = parentFragmentManager;
            if (fm.backStackEntryCount > 0) {
                fm.popBackStack()
            }
        }

    }

    private suspend fun callDistrictApi(stateId: Int, mCity: String) {
            val responses = RetrofitRestClient.instance.getDistrictList(stateId)
            if (responses.data?.district_list?.isNotEmpty() == true) {
                Log.d("TSF_APPS","district list api called..success  response ="+responses.data.district_list);
               for (list in responses.data.district_list) {
                   if(list.key.equals(mCity)){
                       mCityId =  list.value;
                       break;
                   }
               }
            }
    }

    private fun setObservable() {
        locationFragmentViewModel.locationList.observe(baseActivity, Observer {
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

    override fun onClick(item: Any, stateName: Any) {
        (activity as ActivityFragment).replaceFragment(
                DistrictLocationFragment.getInstance(Constant.From.DISTRICT_LOCATION, item as Int, stateName as String),
                null, true, false)
    }


    @SuppressLint("MissingPermission", "SetTextI18n")
    fun getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener { task: Task<Location?> ->
                    val location = task.result
                    if (location == null) {
                        Log.d("TSF_APPS", "Location is null ")
                        requestNewLocationData()
                    } else {
                        Log.d("TSF_APPS", "Latitude Fused:  " + location.latitude)
                        Log.d("TSF_APPS", "Longitude Fused:  " + location.longitude)
                        val geocoder =
                            Geocoder(baseActivity, Locale.getDefault())
                        var addresses: List<Address>? = null
                        try {
                            addresses = geocoder.getFromLocation(
                                location.latitude,
                                location.longitude,
                                1
                            )
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        val cityName = addresses!![0].subAdminArea
                        val stateName = addresses[0].adminArea
                        mState = stateName
                        mCity = cityName

                    }
                }
            } else {
               showToast(baseActivity, "Please turn on" + " your location...")
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions()
        }
    }
    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 5
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(baseActivity)
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        @SuppressLint("SetTextI18n")
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation = locationResult.lastLocation
            Log.d("TSF_APPS", "Latitude:  " + mLastLocation.latitude)
            Log.d("TSF_APPS", "Longitude:  " + mLastLocation.longitude)
            val geocoder = Geocoder(baseActivity, Locale.getDefault())
            var addresses: List<Address>? = null
            try {
                addresses =
                    geocoder.getFromLocation(mLastLocation.latitude, mLastLocation.longitude, 1)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            assert(addresses != null)
            val stateName = addresses!![0].adminArea
            val cityName = addresses[0].subAdminArea

        }
    }

    // method to check for permissions
    private fun checkPermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            (ActivityCompat.checkSelfPermission(
                baseActivity,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(
                baseActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)
        } else {
            (ActivityCompat.checkSelfPermission(
                baseActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(
                baseActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)
        }

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                baseActivity, arrayOf(
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), PERMISSION_ID
            )
        }
        ActivityCompat.requestPermissions(
            baseActivity, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), PERMISSION_ID
        )
    }

    // method to check
    // if location is enabled
    private fun isLocationEnabled(): Boolean {
        val locationManager = baseActivity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    // If everything is alright then
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        Log.d("TSF_APPS", "Request Code: + $requestCode")

        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
                Log.d("TSF_APPS", "Request Call")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (checkPermissions()) {
            getLastLocation()
        }
    }

}