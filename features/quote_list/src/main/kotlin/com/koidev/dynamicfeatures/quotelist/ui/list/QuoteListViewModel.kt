package com.koidev.dynamicfeatures.quotelist.ui.list

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import com.koidev.commons.ui.livedata.SingleLiveData
import com.koidev.core.network.NetworkState
import com.koidev.dynamicfeatures.quotelist.ui.list.paging.QuotePageDataSourceFactory
import com.koidev.dynamicfeatures.quotelist.ui.list.paging.PAGE_MAX_ELEMENTS
import javax.inject.Inject

/**
 * View model responsible for preparing and managing the data for [QuoteListFragment].
 *
 * @see ViewModel
 */
class QuoteListViewModel @Inject constructor(
    @VisibleForTesting(otherwise = PRIVATE)
    val dataSourceFactory: QuotePageDataSourceFactory
) : ViewModel() {

    @VisibleForTesting(otherwise = PRIVATE)
    val networkState = Transformations.switchMap(dataSourceFactory.sourceLiveData) {
        it.networkState
    }

    val event = SingleLiveData<QuoteListViewEvent>()
    val data = LivePagedListBuilder(dataSourceFactory, PAGE_MAX_ELEMENTS).build()
    val state = Transformations.map(networkState) {
        when (it) {
            is NetworkState.Success ->
                if (it.isAdditional && it.isEmptyResponse) {
                    QuoteListViewState.NoMoreElements
                } else if (it.isEmptyResponse) {
                    QuoteListViewState.Empty
                } else {
                    QuoteListViewState.Loaded
                }
            is NetworkState.Loading ->
                if (it.isAdditional) {
                    QuoteListViewState.AddLoading
                } else {
                    QuoteListViewState.Loading
                }
            is NetworkState.Error ->
                if (it.isAdditional) {
                    QuoteListViewState.AddError
                } else {
                    QuoteListViewState.Error
                }
        }
    }

    // ============================================================================================
    //  Public methods
    // ============================================================================================

    /**
     * Refresh characters fetch them again and update the list.
     */
    fun refreshLoadedCharactersList() {
        dataSourceFactory.refresh()
    }

    /**
     * Retry last fetch operation to add characters into list.
     */
    fun retryAddCharactersList() {
        dataSourceFactory.retry()
    }

    /**
     * Send interaction event for open character detail view from selected character.
     *
     * @param characterId Character identifier.
     */
    fun openCharacterDetail(characterId: Long) {
        event.postValue(QuoteListViewEvent.OpenQuoteDetail(characterId))
    }
}
