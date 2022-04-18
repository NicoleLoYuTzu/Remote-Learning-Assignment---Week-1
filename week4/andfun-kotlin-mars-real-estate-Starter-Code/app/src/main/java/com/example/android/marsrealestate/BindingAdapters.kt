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

package com.example.android.marsrealestate

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.marsrealestate.network.MarsProperty
import com.example.android.marsrealestate.overview.MarsApiStatus
import com.example.android.marsrealestate.overview.PhotoGridAdapter

//8-12第十步 Add a binding adapter for the listData attribute that calls submitList on the RV adapter
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<MarsProperty>?) {
    val adapter = recyclerView.adapter as PhotoGridAdapter
    adapter.submitList(data)
}

//8-11第六步 Create the Binding Adapter, converting(轉換) the imgUrl to a URI with the https scheme(方案)
@BindingAdapter("imageUrl")  //當xml有imageUrl屬性時 執行此BindingAdapter
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        //8-11第七步 Use Glide to load the imgUrl into the imgView
        Glide.with(imgView.context)
            .load(imgUri)
            //8-11第十一步 Add the requestOptions(請求選項) for the placeholder(佔位符) and error into the Glide call
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation) //還在加載圖片時顯示的樣式
                .error(R.drawable.ic_broken_image)) //加載圖片失敗顯示的樣式
            .into(imgView)
    }
}

//8-13第四步 Add the binding adapter to show the MarsApi status in the ImageView and show/hide the view
@BindingAdapter("marsApiStatus")
fun bindStatus(statusImageView: ImageView, status: MarsApiStatus?) {
    when (status) {
        MarsApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        MarsApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        MarsApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}



