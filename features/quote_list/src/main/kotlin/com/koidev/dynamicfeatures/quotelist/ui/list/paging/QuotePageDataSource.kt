package com.koidev.dynamicfeatures.quotelist.ui.list.paging

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.koidev.core.annotations.OpenForTesting
import com.koidev.core.network.NetworkState
import com.koidev.dynamicfeatures.quotelist.ui.list.model.QuoteItem
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

const val PAGE_INIT_ELEMENTS = 0
const val PAGE_MAX_ELEMENTS = 50

/**
 * Incremental data loader for page-keyed content, where requests return keys for next/previous
 * pages. Obtaining paginated the Marvel characters.
 *
 * @see PageKeyedDataSource
 */
@OpenForTesting
class QuotePageDataSource @Inject constructor(
//    @VisibleForTesting(otherwise = PRIVATE)
//    val repository: Repository,
    @VisibleForTesting(otherwise = PRIVATE)
    val scope: CoroutineScope
) : PageKeyedDataSource<Int, QuoteItem>() {

    val networkState = MutableLiveData<NetworkState>()
    @VisibleForTesting(otherwise = PRIVATE)
    var retry: (() -> Unit)? = null

    /**
     * Load initial data.
     *
     * @param params Parameters for initial load, including requested load size.
     * @param callback Callback that receives initial load data.
     * @see PageKeyedDataSource.loadInitial
     */
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, QuoteItem>
    ) {
        networkState.postValue(NetworkState.Loading())
        scope.launch(
            CoroutineExceptionHandler { _, _ ->
                retry = {
                    loadInitial(params, callback)
                }
                networkState.postValue(NetworkState.Error())
            }
        ) {
//            val response = repository.getCharacters(
//                offset = PAGE_INIT_ELEMENTS,
//                limit = PAGE_MAX_ELEMENTS
//            )
//            val data = mapper.map(response)
//            callback.onResult(data, null, PAGE_MAX_ELEMENTS)
//            networkState.postValue(NetworkState.Success(isEmptyResponse = data.isEmpty()))
        }
    }

    /**
     * Append page with the key specified by [LoadParams.key].
     *
     * @param params Parameters for the load, including the key for the new page, and requested
     * load size.
     * @param callback Callback that receives loaded data.
     * @see PageKeyedDataSource.loadAfter
     */
    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, QuoteItem>
    ) {
        networkState.postValue(NetworkState.Loading(true))
        scope.launch(
            CoroutineExceptionHandler { _, _ ->
                retry = {
                    loadAfter(params, callback)
                }
                networkState.postValue(NetworkState.Error(true))
            }
        ) {
//            val response = repository.getCharacters(
//                offset = params.key,
//                limit = PAGE_MAX_ELEMENTS
//            )
//            val data = mapper.map(response)
//            callback.onResult(data, params.key + PAGE_MAX_ELEMENTS)
//            networkState.postValue(NetworkState.Success(true, data.isEmpty()))
        }
    }

    /**
     * Prepend page with the key specified by [LoadParams.key]
     *
     * @param params Parameters for the load, including the key for the new page, and requested
     * load size.
     * @param callback Callback that receives loaded data.
     * @see PageKeyedDataSource.loadBefore
     */
    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, QuoteItem>
    ) {
        // Ignored, since we only ever append to our initial load
    }

    /**
     * Force retry last fetch operation in case it has ever been previously executed.
     */
    fun retry() {
        retry?.invoke()
    }
}
