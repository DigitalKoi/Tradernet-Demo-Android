package com.koidev.core.di

import android.content.Context
import com.koidev.core.di.modules.ContextModule
import com.koidev.core.di.modules.DatabaseModule
import com.koidev.core.di.modules.NetworkModule
import com.koidev.core.di.modules.UtilsModule
import com.koidev.core.utils.ThemeUtils
import dagger.Component
import javax.inject.Singleton

/**
 * Core component that all module's components depend on.
 *
 * @see Component
 */
@Singleton
@Component(
    modules = [
        ContextModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        UtilsModule::class
    ]
)
interface CoreComponent {

    /**
     * Provide dependency graph Context
     *
     * @return Context
     */
    fun context(): Context

    /**
     * Provide dependency graph ThemeUtils
     *
     * @return ThemeUtils
     */
    fun themeUtils(): ThemeUtils
}
