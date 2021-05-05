package com.koidev.libraries.testutils.pagelist

import android.annotation.SuppressLint
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.room.RoomDatabase
import androidx.room.RoomSQLiteQuery
import com.koidev.libraries.testutils.datasource.TestLimitDataSource
import com.koidev.libraries.testutils.livedata.getOrAwaitValue
import io.mockk.mockk

fun <T> pagedListOf(vararg elements: T, config: PagedList.Config? = null): PagedList<T>? {
    val defaultConfig = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(elements.count())
        .setMaxSize(elements.count() + 2)
        .setPrefetchDistance(1)
        .build()
    return LivePagedListBuilder<Int, T>(
        createMockDataSourceFactory(elements.toList()),
        config ?: defaultConfig
    ).build().getOrAwaitValue()
}

private fun <T> createMockDataSourceFactory(itemList: List<T>): DataSource.Factory<Int, T> =
    object : DataSource.Factory<Int, T>() {
        @SuppressLint("RestrictedApi")
        override fun create(): DataSource<Int, T> {
            val mockQuery = mockk<RoomSQLiteQuery>(relaxed = true)
            val mockDb = mockk<RoomDatabase>(relaxed = true)

            return TestLimitDataSource(
                db = mockDb,
                query = mockQuery,
                itemList = itemList
            )
        }
    }
