package com.molitics.molitician.ui.dashboard.register.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.molitics.molitician.httpapi.RetrofitRestClient
import com.molitics.molitician.httpapi.awaitResult
import com.molitics.molitician.ui.dashboard.constantData.ConstantModel
import com.molitics.molitician.ui.dashboard.register.UserModel
import com.molitics.molitician.ui.dashboard.register.model.SignInRequestModel
import com.molitics.molitician.ui.dashboard.register.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegisterRepo {

    private val locationList: MutableLiveData<List<ConstantModel>> = MutableLiveData()
    private val districtList: MutableLiveData<List<ConstantModel>> = MutableLiveData()
    private val checkUserName: MutableLiveData<String> = MutableLiveData()

    fun bindLocation(): LiveData<List<ConstantModel>> {
        return locationList
    }

    fun bindDistrictLocation(): LiveData<List<ConstantModel>> {
        return districtList
    }

    suspend fun getStateLocation(stateId: Int?) {
        withContext(Dispatchers.Default) {
            val responses = RetrofitRestClient.instance.getDistrictList(stateId)
            if (responses.data?.district_list?.isNotEmpty() == true) {
                districtList.postValue(responses.data.district_list)
            } else
                locationList.postValue(responses.data?.state_list)
        }
    }

    suspend fun submitRegisterData(user: SignInRequestModel, auth: String) =
        RetrofitRestClient.instance.createAccount(auth, user).awaitResult()

    suspend fun checkUserNameApi(auth: String, user: User) =
        RetrofitRestClient.instance.checkUserName(auth, user).awaitResult()

    suspend fun registerNumberService(user: SignInRequestModel) =
        RetrofitRestClient.instance.sendOtp(user).awaitResult()
}