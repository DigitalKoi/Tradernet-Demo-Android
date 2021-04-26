package com.koidev.dynamicfeatures.quotelist.ui.list.paging

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.koidev.dynamicfeatures.quotelist.ui.list.model.QuoteItem
import javax.inject.Inject
import javax.inject.Provider

/**
 * Data source factory which also provides a way to observe the last created data source.
 * This allows us to channel its network request status etc back to the UI.
 *
 * @see DataSource.Factory
 */
class QuotePageDataSourceFactory
@Inject constructor(
    @VisibleForTesting(otherwise = PRIVATE)
    val providerDataSource: Provider<QuotePageDataSource>
) : DataSource.Factory<Int, QuoteItem>() {

    var sourceLiveData = MutableLiveData<QuotePageDataSource>()

    /**
     * Create a DataSource.
     *
     * @return The new DataSource.
     * @see DataSource.Factory.create
     */
    override fun create(): DataSource<Int, QuoteItem> {
        val dataSource = providerDataSource.get()
        sourceLiveData.postValue(dataSource)
        return dataSource
    }

    /**
     * Force refresh data source by invalidating and re-create again.
     */
    fun refresh() {
        sourceLiveData.value?.invalidate()
    }

    /**
     * Force retry last fetch operation on data source.
     */
    fun retry() {
        sourceLiveData.value?.retry()
    }
}
