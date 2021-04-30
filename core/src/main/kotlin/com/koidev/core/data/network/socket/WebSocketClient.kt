package com.koidev.core.data.network.socket

import com.koidev.core.data.network.response.QuoteResponse
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

    val channel = Channel<QuoteResponse?>()

    private val listener by lazy {
        object : WebSocketListener() {
            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                Timber.d("Closed in onFailure")
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
            }

            override fun onMessage(ws: WebSocket, mess: String) {
                // Called asynchronously when messages arrive
                try {
                    Timber.d("New message in onMessageString: $mess")
                    val fromJson = moshi.fromJson(mess)
                    GlobalScope.launch {
                        channel.send(fromJson)
                        Timber.d("Coroutine scope: ${this}")
                    }
                } catch (e: Exception) {
                    Timber.w("Error getting data with error: ${e.localizedMessage}")
                }
            }
        }
    }

    private val webSocket: WebSocket by lazy {
        OkHttpClient().newWebSocket(request, listener)
    }

    fun startSocketAndSubscribeToData(request: String): Flow<QuoteResponse?> {
        webSocket.send(request)
        return channel.consumeAsFlow()
    }

    fun unsubscribe() {
        webSocket.cancel()
    }
}
