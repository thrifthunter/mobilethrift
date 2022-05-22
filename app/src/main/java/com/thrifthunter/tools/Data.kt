package com.thrifthunter

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    val name: String,
    val photoUrl: String,
    val description: String
    val akun: String,
    val harga: String
) : Parcelable