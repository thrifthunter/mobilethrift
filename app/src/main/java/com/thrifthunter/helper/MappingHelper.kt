package com.thrifthunter.helper

import android.database.Cursor
import com.thrifthunter.Data
import com.thrifthunter.database.DatabaseContract

object MappingHelper {

    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<Data> {
        val favoriteList = ArrayList<Data>()

        notesCursor?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.USERNAME))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.NAME))
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.AVATAR))
                val company = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.COMPANY))
                val location = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.LOCATION))
                val repository = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.REPOSITORY))
                val favorite =
                    getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.FAVORITE))
                favoriteList.add(
                    Data(username, name, avatar, company, location, repository, favorite)
                )
            }
        }
        return favoriteList
    }
}