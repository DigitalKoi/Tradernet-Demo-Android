package com.koidev.dynamicfeatures.quotelist.ui.list

/**
 * Different interaction events for [QuoteListFragment].
 */
sealed class QuoteListViewEvent {

    /**
     * Open Quote detail view.
     *
     * @param id Quote identifier
     */
    data class OpenQuoteDetail(val id: Long) : QuoteListViewEvent()
}
