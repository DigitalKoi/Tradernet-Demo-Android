package com.koidev.dynamicfeatures.home.ui.di

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.koidev.commons.ui.extensions.viewModel
import com.koidev.core.di.scopes.FeatureScope
import com.koidev.dynamicfeatures.home.ui.HomeFragment
import com.koidev.dynamicfeatures.home.ui.HomeViewModel
import dagger.Module
import dagger.Provides

/**
 * Class that contributes to the object graph [HomeComponent].
 *
 * @see Module
 */
@Module
class HomeModule(
    @VisibleForTesting(otherwise = PRIVATE)
    val fragment: HomeFragment
) {

    /**
     * Create a provider method binding for [HomeViewModel].
     *
     * @return Instance of view model.
     * @see Provides
     */
    @Provides
    @FeatureScope
    fun providesHomeViewModel() = fragment.viewModel {
        HomeViewModel()
    }
}
