package com.tsfapps.cahsingusinroom
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CarListViewModel @Inject constructor(
    repository: CarListRepository
) : ViewModel() {
    val cars = repository.getCars().asLiveData()
}