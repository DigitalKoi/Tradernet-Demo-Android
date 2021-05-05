package com.koidev.dynamicfeatures.quotelist.ui.list

import com.koidev.commons.ui.base.BaseViewState

/**
 * Different states for [QuoteListFragment].
 *
 * @see BaseViewState
 */
sealed class QuoteListViewState : BaseViewState {

    /**
     * Refreshing Quote list.
     */
    object Refreshing : QuoteListViewState()

    /**
     * Loaded Quote list.
     */
    object Loaded : QuoteListViewState()

    /**
     * Loading Quote list.
     */
    object Loading : QuoteListViewState()

    /**
     * Empty Quote list.
     */
    object Empty : QuoteListViewState()

    /**
     * Error on loading Quote list.
     */
    object Error : QuoteListViewState()

    /**
     * Error on add more elements into Quote list.
     */
    object AddError : QuoteListViewState()


    // ============================================================================================
    //  Public helpers methods
    // ============================================================================================

    /**
     * Check if current view state is [Refreshing].
     *
     * @return True if is refreshing state, otherwise false.
     */
    fun isRefreshing() = this is Refreshing

    /**
     * Check if current view state is [Loaded].
     *
     * @return True if is loaded state, otherwise false.
     */
    fun isLoaded() = this is Loaded

    /**
     * Check if current view state is [Loading].
     *
     * @return True if is loading state, otherwise false.
     */
    fun isLoading() = this is Loading

    /**
     * Check if current view state is [Empty].
     *
     * @return True if is empty state, otherwise false.
     */
    fun isEmpty() = this is Empty

    /**
     * Check if current view state is [Error].
     *
     * @return True if is error state, otherwise false.
     */
    fun isError() = this is Error

    /**
     * Check if current view state is [AddError].
     *
     * @return True if is add error state, otherwise false.
     */
    fun isAddError() = this is AddError
}
