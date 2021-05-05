package com.koidev.dynamicfeatures.quotelist.ui.list.adapter.holders

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.koidev.commons.ui.base.BaseViewHolder
import com.koidev.dynamicfeatures.quotelist.databinding.ListItemLoadingBinding

/**
 * Class describes quotes loading view and metadata about its place within the [RecyclerView].
 *
 * @see BaseViewHolder
 */
class LoadingViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<ListItemLoadingBinding>(
    binding = ListItemLoadingBinding.inflate(inflater)
)
