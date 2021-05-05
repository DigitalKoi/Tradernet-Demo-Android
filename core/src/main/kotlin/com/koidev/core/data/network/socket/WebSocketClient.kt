package com.koidev.core.data.network.socket

import com.koidev.core.data.network.response.ErrorResponse
import com.koidev.core.data.network.response.QuoteResponse
import com.koidev.core.data.network.response.TradernetResponse
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import okhttp3.*
import okio.ByteString
import timber.log.Timber
import javax.inject.Inject

class WebSocketClient @Inject constructor(
    private val moshi: JsonAdapter<QuoteResponse>,
    private val request: Request
) {

    private val channel = Channel<TradernetResponse>()

    private val listener by lazy {
        object : WebSocketListener() {
            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                Timber.d("Closed in onClosed")

            }

            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                Timber.d("Opened in onOpen")
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
                Timber.d("New message in onMessageBytes: $bytes")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                Timber.d("Closed in onFailure")
                sendEvent(ErrorResponse(message = "Closed in onFailure"))
            }

            override fun onMessage(ws: WebSocket, mess: String) {
                // Called asynchronously when messages arrive
                try {
                    Timber.d("New message in onMessageString: $mess")
                    sendEvent(
                    moshi.fromJson(mess)
                        ?: ErrorResponse(message = "Error getting data")
                    )
                } catch (e: Exception) {
                    Timber.w("Error getting data with error: ${e.localizedMessage}")
                    sendEvent(
                        ErrorResponse(message = "Error getting data")
                    )
                }
            }
        }
    }

    fun startSocketAndSubscribeToData(request: String): Flow<TradernetResponse> {
        webSocket.send(request)
        return channel.consumeAsFlow()
    }

    fun unsubscribe() {
        webSocket.cancel()
    }

    private val webSocket: WebSocket by lazy {
        OkHttpClient().newWebSocket(request, listener)
    }

    private fun sendEvent(response: TradernetResponse) {
        GlobalScope.launch {
            channel.send(response)
        }
    }
}
