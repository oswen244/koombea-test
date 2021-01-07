package com.oswaldo.android.koombeatest.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(val id: Int, val date: String, val pics: List<String>): Parcelable