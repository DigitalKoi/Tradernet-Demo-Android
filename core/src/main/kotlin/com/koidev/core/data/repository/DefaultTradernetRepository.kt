package com.koidev.core.data.repository

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.koidev.core.data.mapper.toDomain
import com.koidev.core.data.mapper.toEntity
import com.koidev.core.data.network.response.QuoteResponse
import com.koidev.core.data.network.socket.WebSocketClient
import com.koidev.core.domain.Quote
import com.koidev.core.domain.repository.TradernetRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.collections.HashMap

class DefaultTradernetRepository @Inject constructor(
    @VisibleForTesting(otherwise = PRIVATE)
    private val webSocketClient: WebSocketClient
) : TradernetRepository {

    private val list = HashMap<String, QuoteResponse>()

    override fun subscribeToQuotes(request: String): Flow<List<Quote>> = webSocketClient
        .startSocketAndSubscribeToData(request)
        .filterNotNull()
        .filter(QuoteResponse::isNotNullableFields)
        .onEach { new ->
            val old = list[new.ticker]
            val entity = new.toEntity(old)
            list[new.ticker] = entity
        }.map {
            list.toList().map(Pair<String, QuoteResponse>::second)
        }.map {
            it.map(QuoteResponse::toDomain)
        }

    override fun unsubscribe() {
        webSocketClient.unsubscribe()
    }
}
