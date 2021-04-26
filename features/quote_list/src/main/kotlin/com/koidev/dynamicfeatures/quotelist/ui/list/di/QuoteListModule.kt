package com.koidev.dynamicfeatures.quotelist.ui.list.di

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.viewModelScope
import com.koidev.core.di.scopes.FeatureScope
import com.koidev.commons.ui.extensions.viewModel
import com.koidev.dynamicfeatures.quotelist.ui.list.adapter.QuoteListAdapter
import com.koidev.dynamicfeatures.quotelist.ui.list.QuoteListFragment
import com.koidev.dynamicfeatures.quotelist.ui.list.QuoteListViewModel
import com.koidev.dynamicfeatures.quotelist.ui.list.paging.QuotePageDataSource
import com.koidev.dynamicfeatures.quotelist.ui.list.paging.QuotePageDataSourceFactory
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
     * @param dataFactory Data source factory for characters.
     * @return Instance of view model.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesQuoteListViewModel(
        dataFactory: QuotePageDataSourceFactory
    ) = fragment.viewModel {
        QuoteListViewModel(dataFactory)
    }

    /**
     * Create a provider method binding for [QuotePageDataSource].
     *
     * @return Instance of data source.
     * @see Provides
     */
    @Provides
    fun providesQuotePageDataSource(
        viewModel: QuoteListViewModel
    ) = QuotePageDataSource(
        scope = viewModel.viewModelScope
    )

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
