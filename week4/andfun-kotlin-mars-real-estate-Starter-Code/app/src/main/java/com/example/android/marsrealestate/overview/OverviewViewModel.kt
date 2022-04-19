/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.marsrealestate.network.MarsApi
import com.example.android.marsrealestate.network.MarsApiFilter
import com.example.android.marsrealestate.network.MarsProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.ArrayList

//8-13第一步 Create a MarsApiStatus enum with the LOADING, ERROR, and DONE states
enum class MarsApiStatus { LOADING, ERROR, DONE }

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the status of the most recent request
//    private val _response = MutableLiveData<String>()
    //8-11第二步 Rename response LiveData to status
//    private val _status = MutableLiveData<String>()
    //8-13第二步 Change _status to type MarsApiStatus
    private val _status = MutableLiveData<MarsApiStatus>()
    val status : LiveData<MarsApiStatus>
        get() = _status

    //8-11第三步 Add the LiveData MarsProperty with an internal(內部的) Mutable and an external LiveData
//    private val _property = MutableLiveData<MarsProperty>()
//    val property : LiveData<MarsProperty>
//    get() = _property
    //8-12第二步 Update the ViewModel to return a LiveData of List<MarsProperty>
    private val _properties = MutableLiveData<List<MarsProperty>>()
    val properties : LiveData<List<MarsProperty>>
        get() = _properties

    //8-15第五步 Add a _navigateToSelectedProperty MutableLiveData externalized as LiveData
//    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty>()
//    val navigateToSelectedProperty : LiveData<MarsProperty>
//        get() = _navigateToSelectedProperty

    // The external immutable LiveData for the request status String
//    val response: LiveData<String>
//        get() = _status

    //8-9第四步 Create a coroutine Job and a Coroutine Scope using the Main Dispatcher
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
//        getMarsRealEstateProperties()
        //8-16第五步 Add MarsApiFilter.SHOW_ALL as a default parameter to the initial getMarsRealEstateProperties
        getMarsRealEstateProperties(MarsApiFilter.SHOW_ALL)
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    //8-6第五步 Call the MarsApi to enqueue(排隊) the Retrofit request(請求), implementing the callbacks
//    private fun getMarsRealEstateProperties() {
        //8-16第三步 Add MarsApiFilter parameter to getMarsRealEstateProperties
    private fun getMarsRealEstateProperties(filter: MarsApiFilter) {
//        MarsApi.retrofitService.getProperties()
        //8-9第五步 Call coroutineScope.launch and place the rest of the code in it
        coroutineScope.launch {
            //8-9第六步 Call MarsApi.retrofitService.getProperties and call await on the Deferred
//            var getPropertiesDeferred = MarsApi.retrofitService.getProperties()
            //8-16第四步 Add filter to getProperties() with filter.value
            var getPropertiesDeferred = MarsApi.retrofitService.getProperties(filter.value)
            try {
                //8-13第三步 Set the correct status for LOADING, ERROR, and DONE
                    _status.value = MarsApiStatus.LOADING //8-13資料讀取完成前為讀取狀態
                var listResult = getPropertiesDeferred.await()
                _status.value = MarsApiStatus.DONE //8-13資料讀取完成後為完成狀態

                //8-9第七步 Surround the Retrofit code with a try/catch, and set _response appropriately(適當的)
//                _status.value = "Success: ${listResult.size} Mars properties retrieved"
                //8-11第四步 Update to set _property to the first MarsProperty from listResult
                if (listResult.size > 0) {
//                    _properties.value = listResult[0]
                    //8-12第三步
                    _properties.value = listResult
                }
            } catch (t: Throwable) {
//                _status.value = "Failure: " + t.message
                _status.value = MarsApiStatus.ERROR //8-13資料讀取失敗為失敗狀態
                _properties.value = ArrayList() //8-13當沒有抓到資料時 顯示為空的array
            }

        }
//            //8-8第七步 Update getMarsRealEstateProperties to handle List<MarsProperty>
////            .enqueue(object : Callback<String> { //在background thread
//            .enqueue(object : Callback<List<MarsProperty>> { //在background thread
//                // override fun onResponse(call: Call<String>, response: Response<String>) {
//                override fun onResponse(
//                    call: Call<List<MarsProperty>>, response: Response<List<MarsProperty>>) {
////                    _response.value = response.body()
//                    _response.value = "Success: ${response.body()?.size} Mars properties retrieved"
//                }
//
//                // override fun onFailure(call: Call<String>, t: Throwable) {
//                override fun onFailure(call: Call<List<MarsProperty>>, t: Throwable) {
//                    _response.value = "Failure: " + t.message
//                }
//
//            })

    }

    //8-9第八步 Cancel the Coroutine Job when the ViewModel is finished in onCleared
    override fun onCleared() {
        super.onCleared()
    }

    //8-15第六步 Add displayPropertyDetails and displayPropertyDetailsComplete methods
//    fun displayPropertyDetails(marsProperty: MarsProperty) {
//        _navigateToSelectedProperty.value = marsProperty
//    }
//    fun displayPropertyDetailsComplete() {
//        _navigateToSelectedProperty.value = null
//    }

    //8-16第六步 Add updateFilter method that takes a filter input and re_gets(更新) the properties
    fun updateFilter(filter: MarsApiFilter) {
        getMarsRealEstateProperties(filter)
    }
}
