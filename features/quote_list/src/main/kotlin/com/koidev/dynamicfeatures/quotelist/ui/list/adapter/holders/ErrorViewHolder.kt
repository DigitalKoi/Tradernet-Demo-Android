package com.koidev.dynamicfeatures.quotelist.ui.list.adapter.holders

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.koidev.commons.ui.base.BaseViewHolder
import com.koidev.dynamicfeatures.quotelist.databinding.ListItemErrorBinding
import com.koidev.dynamicfeatures.quotelist.ui.list.QuoteListViewModel

/**
 * Class describes quotes error view and metadata about its place within the [RecyclerView].
 *
 * @see BaseViewHolder
 */
class ErrorViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<ListItemErrorBinding>(
    binding = ListItemErrorBinding.inflate(inflater)
) {

    /**
     * Bind view data binding variables.
     *
     * @param viewModel quote list view model.
     */
    fun bind(viewModel: QuoteListViewModel) {
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }
}
