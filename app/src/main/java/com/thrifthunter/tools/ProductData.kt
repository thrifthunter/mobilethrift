package com.thrifthunter

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    val name: String,
    val photoUrl: String,
    val description: String,
    val account: String,
    val price: String,
    val category: String
) : Parcelable