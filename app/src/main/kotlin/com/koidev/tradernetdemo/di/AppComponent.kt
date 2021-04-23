package com.koidev.tradernetdemo.di

import com.koidev.tradernetdemo.TradernetApp
import com.koidev.tradernetdemo.di.AppModule
import com.koidev.core.di.CoreComponent
import com.koidev.core.di.scopes.AppScope
import dagger.Component

/**
 * App component that application component's components depend on.
 *
 * @see Component
 */
@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class]
)
interface AppComponent {

    /**
     * Inject dependencies on application.
     *
     * @param application The sample application.
     */
    fun inject(application: TradernetApp)
}
