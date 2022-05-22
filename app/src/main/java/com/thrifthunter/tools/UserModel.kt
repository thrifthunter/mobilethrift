package com.thrifthunter.auth

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    val name: String,
    val email: String,
    val password: String,
    val status: Boolean,
    var token: String
) : Parcelable