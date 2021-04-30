package com.koidev.core.data.network.socket

import com.koidev.core.data.network.response.QuoteResponse
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.ToJson

class QuoteAdapter {

    @FromJson
    fun fromJson(response: Array<Any>): QuoteResponse {

        if (response.first() != QUOTE_EVENT) throw JsonDataException("It\'s not a quote response: $response")

        val body = if (response[1] is Map<*, *>) response[1] as Map<String, Any>
            else throw JsonDataException("It\'s not a quote response: $response")

        return if (body.containsKey(C_KEY)) QuoteResponse(
            ticker = body.getValueByKey(C_KEY) as String,
            pcp = body.getValueByKey(PCP_KEY) as Double?,
            ltr = body.getValueByKey(LTR_KEY) as String?,
            name = body.getValueByKey(NAME_KEY) as String?,
            ltp = body.getValueByKey(LTP_KEY) as Double?,
            chg = body.getValueByKey(CHG_KEY) as Double?
        ) else throw JsonDataException("Can\'t find primary value ticker property: $response")
    }

    @ToJson
    fun toJson(quote: QuoteResponse): String = quote.toString()

    private fun Map<String, Any>.getValueByKey(key: String): Any? =
            if (this.containsKey(key)) this[key]
            else null


    private companion object {
        const val QUOTE_EVENT = "q"

        const val C_KEY = "c"
        const val PCP_KEY = "pcp"
        const val LTR_KEY = "ltr"
        const val NAME_KEY = "name"
        const val LTP_KEY = "ltp"
        const val CHG_KEY = "chg"
    }
}
