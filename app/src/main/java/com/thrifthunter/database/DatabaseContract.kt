package com.thrifthunter.database

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.example.githubusers2"
    const val SCHEME = "content"

    class FavColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite"
            const val USERNAME = "username"
            const val NAME = "name"
            const val AVATAR = "avatar"
            const val COMPANY = "company"
            const val LOCATION = "location"
            const val REPOSITORY = "repository"
            const val FAVORITE = "isFav"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}