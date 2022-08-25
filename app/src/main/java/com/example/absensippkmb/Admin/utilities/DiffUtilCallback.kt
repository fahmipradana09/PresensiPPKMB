package com.example.absensippkmb.Admin.utilities

import androidx.recyclerview.widget.DiffUtil
import com.example.absensippkmb.Admin.data.model.AttendenceModel


class DiffUtilCallback: DiffUtil.ItemCallback<AttendenceModel>() {
    override fun areItemsTheSame(oldItem: AttendenceModel, newItem: AttendenceModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: AttendenceModel, newItem: AttendenceModel): Boolean {
        return when {
//            oldItem.id != newItem.id -> false
//            oldItem.image != newItem.image -> false
//            oldItem.name != newItem.name -> false
//            oldItem.description != newItem.description -> false
//            oldItem.lat != newItem.lat -> false
//            oldItem.lon != newItem.lon -> false
            else -> true
        }
    }
}