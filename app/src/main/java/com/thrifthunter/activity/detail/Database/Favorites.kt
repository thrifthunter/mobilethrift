package com.thrifthunter.activity.detail.Database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Favorites(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val price: Int? = null,
    val photoUrl: String? = null,
    val name: String? = null,
    val description: String? = null,
    val category: String? = null,
    val account: String? = null
)
