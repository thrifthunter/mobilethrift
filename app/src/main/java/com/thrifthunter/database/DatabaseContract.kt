package com.thrifthunter.database

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.example.githubusers2"
    const val SCHEME = "content"

    class FavColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite"
            const val ID = "itemid"
            const val NAME = "name"
            const val PHOTO = "photo"
            const val DESCRIPTION = "description"
            const val ACCOUNT = "account"
            const val PRICE = "price"
            const val CATEGORY = "category"
            const val FAVORITE = "isFav"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}