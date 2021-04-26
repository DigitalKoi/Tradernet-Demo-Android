package com.koidev.dynamicfeatures.quotelist.ui.list.di

import com.koidev.core.di.CoreComponent
import com.koidev.core.di.scopes.FeatureScope
import com.koidev.dynamicfeatures.quotelist.ui.list.QuoteListFragment
import dagger.Component

/**
 * Class for which a fully-formed, dependency-injected implementation is to
 * be generated from [QuoteListModule].
 *
 * @see Component
 */
@FeatureScope
@Component(
    modules = [QuoteListModule::class],
    dependencies = [CoreComponent::class]
)
interface QuoteListComponent {

    /**
     * Inject dependencies on component.
     *
     * @param listFragment Quote list component.
     */
    fun inject(listFragment: QuoteListFragment)
}
