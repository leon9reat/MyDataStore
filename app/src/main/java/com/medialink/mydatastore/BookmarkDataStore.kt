package com.medialink.mydatastore

import android.content.Context
import androidx.datastore.CorruptionException
import androidx.datastore.DataStore
import androidx.datastore.Serializer
import androidx.datastore.createDataStore
import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.coroutines.flow.map
import java.io.InputStream
import java.io.OutputStream

class BookmarkDataStore(context: Context) {
    private val appContext = context.applicationContext;
    private val dataStore: DataStore<Bookmark>

    object BookmarkSerializer : Serializer<Bookmark> {
        override fun readFrom(input: InputStream): Bookmark {
            try {
                return Bookmark.parseFrom(input)
            } catch (e: InvalidProtocolBufferException) {
                throw CorruptionException("cannot read proto.", e)
            }
        }

        override fun writeTo(t: Bookmark, output: OutputStream) {
            return t.writeTo(output)
        }

    }

    init {
        dataStore = appContext.createDataStore(
            fileName = "bookmark.pb",
            serializer = BookmarkSerializer
        )
    }

    val bookmark = dataStore.data
        .map {bookmarkSchema ->
            bookmarkSchema.bookmark
        }

    suspend fun saveBookmark(bookmark: String) {
        dataStore.updateData { currBookmark ->
            currBookmark.toBuilder()
                .setBookmark(bookmark) // dari bookmark.proto
                .build()
        }
    }
}