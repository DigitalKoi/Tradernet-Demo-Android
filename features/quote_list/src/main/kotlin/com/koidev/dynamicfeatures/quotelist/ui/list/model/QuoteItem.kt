package com.koidev.dynamicfeatures.quotelist.ui.list.model

import com.koidev.dynamicfeatures.quotelist.ui.list.QuoteListFragment

/**
 * Model view to display on the screen [QuoteListFragment].
 */
data class QuoteItem(
    val id: Long,
    val name: String,
    val description: String,
    val imageUrl: String
)
