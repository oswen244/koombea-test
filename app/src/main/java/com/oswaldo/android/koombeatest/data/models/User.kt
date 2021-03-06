package com.oswaldo.android.koombeatest.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(val uid: String, val name: String, val email: String?, val profile_pic: String, val post: Post): Parcelable
