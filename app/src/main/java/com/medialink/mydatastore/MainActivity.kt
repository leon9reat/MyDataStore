package com.medialink.mydatastore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

/**
 * https://www.simplifiedcoding.net/jetpack-datastore-tutorial/
 */
class MainActivity : AppCompatActivity() {

    private lateinit var userPref: UserPreferences
    private lateinit var bookmarkDataStore: BookmarkDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userPref = UserPreferences(this)
        bookmarkDataStore = BookmarkDataStore(this)

        btnSaveBookmark.setOnClickListener {
            val bookmark = edTextBookmark.text.toString().trim()
            lifecycleScope.launch {
                //userPref.saveBookmark(bookmark)
                bookmarkDataStore.saveBookmark(bookmark)
            }
        }

        //userPref.bookmark.asLiveData().observe(this, {
        //    tvCurrentBookmark.text = it
        //})
        bookmarkDataStore.bookmark.asLiveData().observe(this, Observer {
            tvCurrentBookmark.text = it
        })
    }
}