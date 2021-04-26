package com.koidev.dynamicfeatures.quotelist.ui.list.adapter.holders

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.koidev.commons.ui.base.BaseViewHolder
import com.koidev.dynamicfeatures.quotelist.databinding.ListItemQuoteBinding
import com.koidev.dynamicfeatures.quotelist.ui.list.QuoteListViewModel
import com.koidev.dynamicfeatures.quotelist.ui.list.model.QuoteItem

/**
 * Class describes quote view and metadata about its place within the [RecyclerView].
 *
 * @see BaseViewHolder
 */
class QuoteViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<ListItemQuoteBinding>(
    binding = ListItemQuoteBinding.inflate(inflater)
) {

    /**
     * Bind view data binding variables.
     *
     * @param viewModel Quote list view model.
     * @param item Quote list item.
     */
    fun bind(viewModel: QuoteListViewModel, item: QuoteItem) {
        binding.viewModel = viewModel
        binding.quote = item
        binding.executePendingBindings()
    }
}
