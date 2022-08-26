package me.rezapour.sixttask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.rezapour.sixttask.data.repository.CarsRepository
import me.rezapour.sixttask.model.Car
import me.rezapour.sixttask.utils.DataState
import javax.inject.Inject

@HiltViewModel
class CarListViewModel @Inject constructor(private val repository: CarsRepository) : ViewModel() {

    private val _carDataState: MutableLiveData<DataState<List<Car>>> = MutableLiveData()
    val carDataState: LiveData<DataState<List<Car>>> get() = _carDataState

    fun loadData() {
        _carDataState.value = DataState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCars().collect() { dataState ->
                _carDataState.postValue(dataState)
            }
        }
    }

}