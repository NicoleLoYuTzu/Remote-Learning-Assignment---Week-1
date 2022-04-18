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

package com.example.android.marsrealestate.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//8-8第四步 Use the Moshi Builder to create a Moshi object with the KotlinJsonAdapterFactory
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//8-6第二步 Use Retrofit Builder with ScalarsConverterFactory and BASE_URL
private const val BASE_URL = "https://mars.udacity.com/"

//8-16第一步 Create an enum full of constants to match the query(查詢) values our web service expects(期望)
enum class MarsApiFilter(val value: String) { SHOW_RENT("rent"), SHOW_BUY("buy"), SHOW_ALL("all") }

private val retrofit = Retrofit.Builder()
//    .addConverterFactory(ScalarsConverterFactory.create())
    //8-8第五步 Change the ConverterFactory to the MoshiConverterFactory with our Moshi Object
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    //8-9第二步 Use .addCallAdapterFactory to add the CoroutineCallAdapterFactory
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

//8-6第三步 Implement the MarsApiService interface with @GET gerProperties returning a String
interface MarsApiService {
    @GET("realestate") //會加到BASE_URL網址的後面
//    fun getProperties():
    //8-16第二步 Add filter @Query value to the getProperties method
    fun getProperties(@Query("filter") type: String):

//            Call<String>
    //8-8第六步 Update the MarsApiService to return a List of MarsProperty objects
//            Call<List<MarsProperty>>
    //8-9第三步 Change the return type from our getProperties call to Deferred(延遲)
            Deferred<List<MarsProperty>>
}

//8-6第四步 Create the MarsApi object using Retrofit to implement the MarsApiService
object MarsApi {
    val retrofitService: MarsApiService by lazy { //延遲初始化
        retrofit.create(MarsApiService::class.java)
    }
}

