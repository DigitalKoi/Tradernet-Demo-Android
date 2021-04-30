package com.koidev.core.di.modules

import com.koidev.core.BuildConfig
import com.koidev.core.di.CoreComponent
import com.koidev.core.data.network.response.QuoteResponse
import com.koidev.core.data.network.socket.QuoteAdapter
import com.koidev.core.data.network.socket.WebSocketClient
import com.koidev.core.data.repository.DefaultTradernetRepository
import com.koidev.core.domain.repository.TradernetRepository
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

/**
 * Class that contributes to the object graph [CoreComponent].
 *
 * @see Module
 */
@Module
class NetworkModule {

    /**
     * Create a provider method binding for [HttpLoggingInterceptor].
     *
     * @return Instance of http interceptor.
     * @see Provides
     */
    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    /**
     * Create a provider method binding for [OkHttpClient].
     *
     * @return Instance of http client.
     * @see Provides
     */
    @Singleton
    @Provides
    fun provideHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(interceptor)
        }
        return clientBuilder.build()
    }

    /**
     * Create a provider method binding for [Request]
     *
     * @return Instance of request builder
     * @see Provides
     */
    @Singleton
    @Provides
    fun provideRequest(): Request = Request
        .Builder()
        .url(BuildConfig.TRADERNET_API_BASE_URL)
        .build()

    /**
     * Create a provider method binding for [Moshi.Builder]
     *
     * @return Instance of moshi builder
     * @see Provides
     */
    @Singleton
    @Provides
    fun provideMoshi(): JsonAdapter<QuoteResponse> = Moshi.Builder()
        .add(QuoteAdapter())
        .build()
        .adapter(QuoteResponse::class.java)

    /**
     * Create a provider method binding for [TradernetRepository].
     *
     * @return Instance of tradernet repository.
     * @see Provides
     */
    @Singleton
    @Provides
    fun provideTradernetRepository(webSocketClient: WebSocketClient) = DefaultTradernetRepository(
        webSocketClient = webSocketClient
    ) as TradernetRepository
}
