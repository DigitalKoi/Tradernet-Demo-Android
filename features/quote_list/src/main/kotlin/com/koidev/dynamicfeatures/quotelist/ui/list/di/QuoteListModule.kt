package com.koidev.dynamicfeatures.quotelist.ui.list.di

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.koidev.commons.ui.extensions.viewModel
import com.koidev.core.di.scopes.FeatureScope
import com.koidev.core.domain.repository.TradernetRepository
import com.koidev.dynamicfeatures.quotelist.ui.list.QuoteListFragment
import com.koidev.dynamicfeatures.quotelist.ui.list.QuoteListViewModel
import com.koidev.dynamicfeatures.quotelist.ui.list.adapter.QuoteListAdapter
import dagger.Module
import dagger.Provides

/**
 * Class that contributes to the object graph [QuoteListComponent].
 *
 * @see Module
 */
@Module
class QuoteListModule(
    @VisibleForTesting(otherwise = PRIVATE)
    val fragment: QuoteListFragment
) {

    /**
     * Create a provider method binding for [QuoteListViewModel].
     *
     * @param tradernetData Data source factory for quotes.
     * @return Instance of view model.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesQuoteListViewModel(
        tradernetData: TradernetRepository
    ) = fragment.viewModel {
        QuoteListViewModel(tradernetData)
    }

    /**
     * Create a provider method binding for [QuoteListAdapter].
     *
     * @return Instance of adapter.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesQuoteListAdapter(
        viewModel: QuoteListViewModel
    ) = QuoteListAdapter(viewModel)
}
