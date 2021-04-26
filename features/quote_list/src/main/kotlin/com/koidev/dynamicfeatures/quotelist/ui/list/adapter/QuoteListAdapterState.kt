package com.koidev.dynamicfeatures.quotelist.ui.list.adapter

import com.koidev.commons.ui.base.BaseViewState

/**
 * Different states for [QuoteListAdapter].
 *
 * @see BaseViewState
 */
sealed class QuoteListAdapterState(
    val hasExtraRow: Boolean
) {

    /**
     * Listed the added quotes into list.
     */
    object Added : QuoteListAdapterState(hasExtraRow = true)

    /**
     * Loading for new quotes to add into list.
     */
    object AddLoading : QuoteListAdapterState(hasExtraRow = true)

    /**
     * Error on add new quotes into list.
     */
    object AddError : QuoteListAdapterState(hasExtraRow = true)

    /**
     * No more quotes to add into list.
     */
    object NoMore : QuoteListAdapterState(hasExtraRow = false)

    // ============================================================================================
    //  Public helpers methods
    // ============================================================================================

    /**
     * Check if current view state is [Added].
     *
     * @return True if is added state, otherwise false.
     */
    fun isAdded() = this is Added

    /**
     * Check if current view state is [AddLoading].
     *
     * @return True if is add loading state, otherwise false.
     */
    fun isAddLoading() = this is AddLoading

    /**
     * Check if current view state is [AddError].
     *
     * @return True if is add error state, otherwise false.
     */
    fun isAddError() = this is AddError

    /**
     * Check if current view state is [NoMore].
     *
     * @return True if is no more elements state, otherwise false.
     */
    fun isNoMore() = this is NoMore
}
