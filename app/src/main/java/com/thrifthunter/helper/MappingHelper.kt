package com.thrifthunter.helper

import android.database.Cursor
import com.thrifthunter.database.DatabaseContract
import com.thrifthunter.tools.FavoriteData

object MappingHelper {

    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<FavoriteData> {
        val favoriteList = ArrayList<FavoriteData>()

        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavColumns.ID))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.NAME))
                val photo = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.PHOTO))
                val description = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.DESCRIPTION))
                val account = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.ACCOUNT))
                val price = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.PRICE))
                val category = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.CATEGORY))
                val favorite = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.FAVORITE))
                favoriteList.add(
                    FavoriteData(id, name, photo, description, account, price, category, favorite)
                )
            }
//            val name: String,
//            val photoUrl: String,
//            val description: String,
//            val account: String,
//            val price: String,
//            val category: String,
//            var isFav: String? = null
        }
        return favoriteList
    }
}