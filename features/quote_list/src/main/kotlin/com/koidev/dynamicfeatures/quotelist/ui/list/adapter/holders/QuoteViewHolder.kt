package com.koidev.dynamicfeatures.quotelist.ui.list.adapter.holders

import android.view.LayoutInflater
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.koidev.commons.ui.base.BaseViewHolder
import com.koidev.commons.ui.extensions.setFadedBackgroundTextColor
import com.koidev.core.domain.model.PcpUpdateStatus
import com.koidev.dynamicfeatures.quotelist.databinding.ListItemQuoteBinding
import com.koidev.dynamicfeatures.quotelist.ui.list.QuoteListViewModel
import com.koidev.core.domain.model.Quote
import com.koidev.dynamicfeatures.quotelist.R

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

    private val pcpView: TextView = this.itemView.findViewById<TextView>(R.id.pcp)

    /**
     * Bind view data binding variables.
     *
     * @param viewModel Quote list view model.
     * @param item Quote list item.
     */
    fun bind(viewModel: QuoteListViewModel, item: Quote) {
        binding.viewModel = viewModel
        binding.quote = item
        binding.executePendingBindings()


        val colorAnimation = ContextCompat.getColor(
            this.itemView.context,
            when (item.pcp.status) {
                PcpUpdateStatus.RED -> android.R.color.holo_red_dark
                PcpUpdateStatus.GREEN -> android.R.color.holo_green_dark
                else -> return
            }
        )

        if (item.pcp.time + 300 > System.currentTimeMillis()) {
            pcpView.setFadedBackgroundTextColor(colorAnimation)
        }
    }
}
