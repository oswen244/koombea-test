package com.oswaldo.android.koombeatest.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostResponse(val data: List<User>, val statusCode: Int?, val statusMessage: String): Parcelable