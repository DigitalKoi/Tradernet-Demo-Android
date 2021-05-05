package com.koidev.dynamicfeatures.quotelist.ui.list

import androidx.annotation.NonNull
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koidev.commons.ui.livedata.SingleLiveData
import com.koidev.core.data.network.NetworkState
import com.koidev.core.domain.repository.TradernetRepository
import com.koidev.core.domain.model.Quote
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * View model responsible for preparing and managing the data for [QuoteListFragment].
 *
 * @see ViewModel
 */
class QuoteListViewModel @Inject constructor(
    @VisibleForTesting(otherwise = PRIVATE)
    val tradernetData: TradernetRepository
) : ViewModel() {

    private val networkState = MutableLiveData<NetworkState>()
    val data = MutableLiveData<List<Quote>>()
    val event = SingleLiveData<QuoteListViewEvent>()
    val state = Transformations.map(networkState) {
        when (it) {
            is NetworkState.Success ->
                if (it.isEmptyResponse) {
                    QuoteListViewState.Empty
                } else {
                    QuoteListViewState.Loaded
                }
            is NetworkState.Loading ->
                QuoteListViewState.Loading
            is NetworkState.Error ->
                if (it.isAdditional) {
                    QuoteListViewState.AddError
                } else {
                    QuoteListViewState.Error
                }
        }
    }

    @VisibleForTesting(otherwise = PRIVATE)
    var retry: (() -> Unit)? = null


    init {
        subscribeQuoteData()
    }

    override fun onCleared() {
        super.onCleared()
        tradernetData.unsubscribe()
    }

    /**
     * Retry last fetch operation to add characters into list.
     */
    fun retryAddQuoteList() {
        subscribeQuoteData()
    }

    private fun subscribeQuoteData() {
        networkState.postValue(NetworkState.Loading())
        viewModelScope.launch(
            context = CoroutineExceptionHandler { _, _ ->
                retry = { subscribeQuoteData() }
                networkState.postValue(NetworkState.Error())
            }
        ) {
            tradernetData.subscribeToQuotes(REQUEST).collect { newQuote ->
                data.postValue(newQuote)
                networkState.postValue(NetworkState.Success(isEmptyResponse = newQuote.isEmpty()))
            }
        }
    }

    private companion object {
        const val REQUEST = "[" +
            "\"realtimeQuotes\", " +
            "[" +
            "\"RSTI\"," +
            "\"GAZP\",\"MRKZ\",\"RUAL\",\"HYDR\",\"MRKS\",\"SBER\",\"FEES\",\"TGKA\"," +
            "\"VTBR\",\"ANH.US\",\"VICL.US\",\"BURG.US\",\"NBL.US\",\"YETI.US\",\"WSFS.US\"," +
            "\"NIO.US\",\"DXC.US\",\"MIC.US\",\"HSBC.US\",\"EXPN.EU\",\"GSK.EU\",\"SHP.EU\"," +
            "\"MAN.EU\",\"DB0.EU\",\"MUV2.EU\",\"TATE.EU\",\"KGF.EU\",\"MGGT.EU\",\"SGGD.EU\"" +
            "]" +
            "]"
    }
}
