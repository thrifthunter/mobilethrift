package com.thrifthunter.activity.favorite

import android.content.Intent
import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.thrifthunter.R
import com.thrifthunter.database.DatabaseContract.FavColumns.Companion.CONTENT_URI
import com.thrifthunter.helper.MappingHelper
import com.thrifthunter.tools.FavoriteData
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class FavoriteActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var adapter: FavoriteAdapter

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        setActionBarTitle()

        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.setHasFixedSize(true)
        adapter = FavoriteAdapter(this)
        recycleView.adapter = adapter

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val favoritehandler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(favoritehandler) {
            override fun onChange(self: Boolean) {
                loadAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadAsync()
        } else {
            val list: ArrayList<FavoriteData>? = savedInstanceState.getParcelableArrayList<FavoriteData>(
                EXTRA_STATE
            )
            if (list != null) {
                adapter.listFavorite = list
            }
        }
    }

    private fun setActionBarTitle() {
        if (supportActionBar != null) {
            supportActionBar?.title = "Favorite Users"
        }
    }

    private fun loadAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            progressBar.visibility = View.VISIBLE
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val favData = deferredNotes.await()
            progressBar.visibility = View.INVISIBLE
            if (favData.size > 0) {
                adapter.listFavorite = favData
            } else {
                adapter.listFavorite = ArrayList()
                showMessage()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listFavorite)
    }

    private fun showMessage() {
        Toast.makeText(this, getString(R.string.empty_favorite), Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        loadAsync()
    }

    override fun onClick(p0: View?) {
        val i = Intent(this@FavoriteActivity, FavoriteActivity::class.java)
        finish()
        overridePendingTransition(0, 0)
        startActivity(i)
        overridePendingTransition(0, 0)
    }
}
