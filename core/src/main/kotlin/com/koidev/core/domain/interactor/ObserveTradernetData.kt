package com.koidev.core.domain.interactor

import com.koidev.core.data.network.response.QuoteResponse
import com.koidev.core.data.network.socket.WebSocketClient
import com.koidev.core.domain.base.SubjectInteractor
import com.koidev.core.domain.repository.TradernetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject


//class ObserveTradernetData @Inject constructor(
//    private val repository: TradernetRepository
//) : SubjectInteractor<ObserveTradernetData.Params, QuoteResponse>() {
//
//    override fun createObservable(params: Params): Flow<QuoteResponse> =
//        repository.subscribeToData(params.request)
//
//    data class Params(val request: String)
//}
