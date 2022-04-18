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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Transformations
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsrealestate.R
import com.example.android.marsrealestate.databinding.FragmentDetailBinding
import com.example.android.marsrealestate.databinding.GridViewItemBinding
import com.example.android.marsrealestate.network.MarsProperty

//8-12第六步 Create PhotoGridAdapter that extends the RecyclerView ListAdapter with DiffCallback
class PhotoGridAdapter :
    ListAdapter<MarsProperty, PhotoGridAdapter.MarsPropertyViewHolder>(DiffCallback) {
//8-15第八步 Have PhotoGridAdapter take the OnClickListener class as a constructor property parameter
//class PhotoGridAdapter(private val onClickListener: OnClickListener) :
//    ListAdapter<MarsProperty, PhotoGridAdapter.MarsPropertyViewHolder>(DiffCallback) {
    //8-12第八步 Create and implement the MarsPropertyViewHolder inner class
    class MarsPropertyViewHolder(private var binding: FragmentDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(marsProperty: MarsProperty) {
            binding.property = marsProperty

            binding.executePendingBindings()
        }
    }

    //8-12第七步 Add unimplemented members for PhotoGridAdapter, create and implement DiffCallback companion
    companion object DiffCallback : DiffUtil.ItemCallback<MarsProperty>() {
        override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem === newItem  //這邊是三個等號 判斷引用是否相等
        }

        override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem == newItem
        }

    }

    //8-12第九步 Override and implement onCreateViewHolder and onBindViewHolder
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoGridAdapter.MarsPropertyViewHolder {
        return MarsPropertyViewHolder(FragmentDetailBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PhotoGridAdapter.MarsPropertyViewHolder, position: Int) {
        val marsProperty = getItem(position)

        //8-15第九步 Call the onClick Function from the onClickListener in a lambda from setOnClickListener
//        holder.itemView.setOnClickListener {
//            onClickListener.onClick(marsProperty)
//        }
        holder.bind(marsProperty)
    }

    //8-15第七步 Create an OnClickListener class with a lambda in its constructor that initializes a
    class OnClickListener(val clickListener: (marsProperty: MarsProperty) -> Unit) {
        fun onClick(marsProperty: MarsProperty) = clickListener(marsProperty)
    }


}





