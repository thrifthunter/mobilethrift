package com.thrifthunter.tools

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    val name: String,
    val email: String,
    val password: String,
    val phone: String,
    val status: Boolean,
    var token: String
) : Parcelable